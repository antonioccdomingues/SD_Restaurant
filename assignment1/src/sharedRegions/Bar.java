package sharedRegions;

import entities.*;
import FIFO.*;
import main.*;

public class Bar extends Thread
{
    private int firstStudentID;
    private int lastStudentID=-1;
    private int studentsSeat=0;
    private int studentsEntered;
    private int studentsDone;
    private int coursesEaten=0;
    private boolean allStudentsServed = false;
    private boolean firstStudent = true;
    private boolean orderDone = false;
    private boolean payBill = false;
    private boolean portionCollected = false;
    private boolean portionReady = false;
    private boolean nextCourseIsReady = false;
    private boolean waiterIsRequested = false;
    private boolean waitingForCourse = false;
    private boolean nextPortionReady = false;
    private boolean lastStudentSignaled = false;
    private boolean orderIsDone = false;
    private boolean allStudentsLeft = false;
    private final Student[] students;
    private MemFIFO<Integer> queue;
    private final GeneralRepos repos;   //references to general repository

    /**
	*  Bar instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Bar(GeneralRepos repos)
    {
        students = new Student[MainProgram.students_number];
        for(int i=0; i<MainProgram.students_number;i++)
        {
            students[i] = null;
        }

        try 
        {
            queue = new MemFIFO<>(new Integer[MainProgram.students_number]);
        } catch (MemException e) 
        {
            e.printStackTrace();
        }
        this.repos = repos;
    }

    public synchronized void saluteTheClient()
    {
        //Dequeue the student from the fifo and salute him
        // so he can transition to the next state
        int student_saluted=0;

        // Dequeue the student
        try {
            student_saluted = queue.read();
        } catch (MemException e) {
            e.printStackTrace();
        }

        ((Waiter) Thread.currentThread()).setState(WaiterState.PRESENTING_THE_MENU);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

        students[student_saluted].setSalutedByWaiter();
        // set the seat of the student upon being saluted
        students[student_saluted].setTableSeat(this.studentsSeat);
        this.studentsSeat++;

        notifyAll();

        // After being in presenting the menu state
        // we wait here, before returning to the bar
        // untill the student has read the menu
        while(!students[student_saluted].getReadTheMenu())
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void alertTheWaiter()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.DELIVERING_THE_PORTIONS);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.portionReady = true;
        this.waiterIsRequested = true;
        notifyAll();
    }

    public synchronized void returningToTheBar()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
    }

    public synchronized void prepareTheBill()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.PROCESSING_THE_BILL);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
    }

    public synchronized int lookAround()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

        // while the waiter is not called to be 
        // awakened by other events, he simply waits here

        while(!this.waiterIsRequested)
        {
            try {
                wait();
            } catch (InterruptedException e) 
            {
            }
        }
        
        // After the wait block, means he is requested
        // Depending on the flag that is activated, he will 
        // do some action

        //means there's students at the door, waiting to be saluted
        if(this.queue.getN()!=0)
        {
            this.waiterIsRequested = false;
            return 0;
        }
        else if(this.orderDone)
        {
            this.orderDone = false;
            return 1;
        }
        else if(this.portionReady & this.waitingForCourse)
        {
            this.portionReady = false;
            this.waitingForCourse = false;
            this.nextCourseIsReady = true;
            return 2;
        }
        else if(this.payBill == true)
        {
            this.payBill = false;
            return 3;
        }
        else if(this.allStudentsLeft)
        {
            ((Waiter) Thread.currentThread()).setCanGoHome();
            return 4;
        }
        else
        {
            this.waiterIsRequested = false;
            return -1;
        }
    }

    public synchronized boolean sayGoodbye()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        // While we haven't said goodbye to all students
        // If we've said goodbye to everyone, the waiter can go home
        ((Waiter) Thread.currentThread()).setCanGoHome();
        return true;
    }

    public synchronized void signalTheWaiter(int sID)
    {
        ((Student) Thread.currentThread()).setState(StudentState.CHATTING_WITH_COMPANIONS);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        boolean last = ((Student) Thread.currentThread()).lastStudent;
        // lasr student notified the waiter, and thus
        // wont be waiting in the cycle
        if(last)
        {
            ((Student) Thread.currentThread()).lastStudent = false;
            this.waitingForCourse = true;
            this.coursesEaten++;
            if(this.coursesEaten==3)
                this.orderIsDone = true;
            System.out.printf("Student[%d] was last to eat, will alert the waiter\n", sID);
            //notify the waiter
            this.waiterIsRequested = true;
            notifyAll();
        }
    }

    public synchronized void callTheWaiter()
    {
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.orderDone = true;
        this.waitingForCourse = true;
        // wake up waiter
        this.waiterIsRequested = true;
        notifyAll();
    }

    public synchronized void enter()
    {
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());

        try {
            queue.write(sID);
            this.studentsEntered++;
        } catch (MemException e1) {
            e1.printStackTrace();
        }

        if(firstStudent)
        {
            this.firstStudent = false;
            this.firstStudentID = sID;
            System.out.printf("First Student %d \n", this.firstStudentID);
        }
        // means this is the last student thread
        if(this.studentsEntered==7)
        {
            this.lastStudentID = sID;
            System.out.printf("Last Student %d \n", this.lastStudentID);
        }

        // Wake waiter
        this.waiterIsRequested = true;
        notifyAll();

        // Update the state
        students[sID].setState(StudentState.TAKING_A_SEAT_AT_THE_TABLE);
        repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());

        // block while it is not saluted
        while(!students[sID].getSalutedByWaiter())
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean FirstStudent(int sID)
    {
        if(this.firstStudentID == sID)
        {
            return true;
        }
        else
            return false;
    }

    public synchronized void exit()
    {
        ((Student) Thread.currentThread()).setState(StudentState.GOING_HOME);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.studentsDone++;

        if(this.studentsDone==7)
        {
            this.allStudentsLeft = true;
            this.waiterIsRequested = true;
            notifyAll();
        }
    }

    public synchronized boolean shouldHaveArrivedEarlier(int sID)
    {
        ((Student) Thread.currentThread()).setState(StudentState.PAYING_THE_BILL);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        if(this.lastStudentID == sID)
        {
            this.waiterIsRequested = true;
            this.payBill = true;
            notifyAll();
            return true; 
        }
        else
            return false;
    }

    public synchronized void readTheMenu()
    {
        // Means he transitioned from previous state, having selected the course
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID].setReadTheMenu();

        notifyAll();

        ((Student) Thread.currentThread()).setState(StudentState.SELECTING_THE_COURSES);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
    }
}