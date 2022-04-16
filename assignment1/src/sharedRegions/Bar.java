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
    private boolean portionCollected = false;
    private boolean portionReady = false;
    private boolean nextCourseIsReady = false;
    private boolean waiterIsRequested = false;
    private boolean waitingForCourse = false;
    private boolean nextPortionReady = false;
    private boolean lastStudentSignaled = false;
    private boolean orderIsDone = false;
    private final Student[] students;
    private MemFIFO<Integer> queue;


    public Bar()
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
        this.portionReady = true;
        this.waiterIsRequested = true;
        notifyAll();
    }

    public synchronized void returningToTheBar()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);
    }

    public synchronized void prepareTheBill()
    {
    }

    public synchronized int lookAround()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);


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
        else if(this.studentsDone==7)
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

        notifyAll();
        // While we haven't said goodbye to all students
        while(!this.allStudentsServed)
        {
            try {
                System.out.printf("goodbye");
                wait();
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // If we've said goodbye to everyone, the waiter can go home
        ((Waiter) Thread.currentThread()).setCanGoHome();
        return true;
    }

    public synchronized void signalTheWaiter(int sID)
    {
        ((Student) Thread.currentThread()).setState(StudentState.CHATTING_WITH_COMPANIONS);
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
            System.out.printf("ultimo foi %d\n",sID);
            //notify the waiter
            this.waiterIsRequested = true;
            notifyAll();
        }
    }

    public synchronized void callTheWaiter()
    {
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);
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
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID].setState(StudentState.GOING_HOME);
        this.studentsDone++;
        notifyAll();
    }

    public synchronized boolean shouldHaveArrivedEarlier(int sID)
    {
        if(this.lastStudentID == sID)
            return true; 
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
    }

}
