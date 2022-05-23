package serverSide.sharedRegions;

import serverSide.entities.*;
import commInfra.*;
import serverSide.main.Constants;
import serverSide.stubs.*;;

public class Bar extends Thread
{
    private int firstStudentID;
    private int lastStudentID=-1;
    private int studentsSeat=0;
    private int studentsEntered;
    private int studentsDone;
    private int waiterIsRequested = 0;
    private int studentAtDoor = 0;
    private int portionReady = 0;
    private int courses = 0;
    private double delay = 0;
    private boolean firstStudent = true;
    private boolean orderDone = false;
    private boolean payBill = false;
    //private boolean portionReady = false;
    //private boolean waiterIsRequested = false;
    private boolean allStudentsLeft = false;
    //private boolean studentAtDoor = false;
    private final Student[] students;
    private MemFIFO<Integer> queue;
    private final GeneralReposStub repos;   //references to general repository

    /**
	*  Bar instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Bar(GeneralReposStub repos)
    {
        students = new Student[Constants.students_number];
        for(int i=0; i<Constants.students_number;i++)
        {
            students[i] = null;
        }

        try 
        {
            queue = new MemFIFO<>(new Integer[Constants.students_number]);
        } catch (MemException e) 
        {
            e.printStackTrace();
        }
        this.repos = repos;
        this.delay = 300 * Math.random();
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
        // wake student
        notifyAll();

        // set the seat of the student upon being saluted
        students[student_saluted].setTableSeat(this.studentsSeat);
        this.studentsSeat++;


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
        this.portionReady++;
        this.waiterIsRequested++; 
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

        while(this.waiterIsRequested == 0)
        {
            try {
                wait();
            } catch (InterruptedException e) 
            {
            }
        }
        this.waiterIsRequested--; 
        
        // After the wait block, means he is requested
        // Depending on the flag that is activated, he will 
        // do some action

        //means there's students at the door, waiting to be saluted
        if(this.studentAtDoor > 0)
        {
            this.studentAtDoor--; 
            return 0;
        }
        else if(this.orderDone)
        {
            this.orderDone = false;
            return 1;
        }
        else if(this.portionReady > 0)// && this.waitingForCourse)
        {
            this.portionReady--; 
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
            return -1;
        }
    }

    public synchronized void sayGoodbye()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        // While we haven't said goodbye to all students
        // If we've said goodbye to everyone, the waiter can go home
        ((Waiter) Thread.currentThread()).setCanGoHome();
    }

    public synchronized void signalTheWaiter(int sID)
    {
        ((Student) Thread.currentThread()).setState(StudentState.CHATTING_WITH_COMPANIONS);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        boolean last = ((Student) Thread.currentThread()).isLastStudent();
        // last student notified the waiter, and thus
        // wont be waiting in the cycle
        if(last)
        {
            ((Student) Thread.currentThread()).setLastStudent(false);
            System.out.printf("Student[%d] was last to eat, will alert the waiter\n", sID);
            //notify the waiter
            this.courses ++;
            this.waiterIsRequested++; 
            notifyAll();
        }
    }

    public synchronized void callTheWaiter()
    {
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.orderDone = true;
        // wake up waiter
        this.waiterIsRequested++;
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

        // Wake waiter
        this.studentAtDoor++; 
        this.waiterIsRequested++; 
        notifyAll();

        if(firstStudent)
        {
            this.firstStudent = false;
            this.firstStudentID = sID;
            System.out.printf("First Student %d \n", this.firstStudentID);
        }
        // means this is the last student thread
        if(this.studentsEntered==Constants.students_number)
        {
            this.lastStudentID = sID;
            System.out.printf("Last Student %d \n", this.lastStudentID);
        }


        // block while it is not saluted
        while(!students[sID].getSalutedByWaiter())
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Update the state
        students[sID].setState(StudentState.TAKING_A_SEAT_AT_THE_TABLE);
        repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());
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

        if(this.studentsDone==Constants.students_number)
        {
            this.allStudentsLeft = true;
            this.waiterIsRequested++; 
            notifyAll();
        }
    }

    public synchronized boolean shouldHaveArrivedEarlier(int sID)
    {
        if(this.lastStudentID == sID)
        {
            ((Student) Thread.currentThread()).setState(StudentState.PAYING_THE_BILL);
            int studentId = ((Student) Thread.currentThread ()).getID();
            repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
            this.waiterIsRequested++;
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
        repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());
    }
}