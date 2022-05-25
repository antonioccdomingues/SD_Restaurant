package serverSide.sharedRegions;

import serverSide.entities.*;
import serverSide.main.Constants;
import serverSide.stubs.*;
import commInfra.*;


public class Table extends Thread
{
    private boolean orderDescribed = false;
    public boolean everyBodyFinished = false;
    private boolean firstStudentJoinedTalk = false;
    private boolean billIsReady = false;
    private boolean billIsPaid = false;
    private int studentSelectedCourses;
    private int studentServed = 0;
    private int studentFinishedEating;
    private int coursesDelivered = 0;
    private int studentWaiting = 0;
    private double delay = 0;
    private final Student[] students;
    private MemFIFO<Integer> queue;
    private final GeneralReposStub repos;   //references to general repository    

    /**
	*  Table instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Table(GeneralReposStub repos)
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

    public synchronized void getThePad()
    {
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.TAKING_THE_ORDER);
        //repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

        while(!this.orderDescribed)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void deliverPortion()
    {
        int student_served=0;

        while(this.studentWaiting == 0)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.studentWaiting--;
        // Dequeue the student
        try {
            student_served = queue.read();
        } catch (MemException e) 
        {
            System.out.println("No One to be served");
            System.out.println("But counted one as delivered");
        }

        students[student_served].setServedByWaiter(true);
        notifyAll();
    }

    public synchronized boolean haveAllClientsBeenServed()
    {
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.WAITING_FOR_PORTION);
        //repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        if(this.studentServed==Constants.students_number)
        {
            this.studentServed=0;
            return true;
        }
        else
            return false;
    }

    public synchronized void presentTheBill()
    {
        this.billIsReady = true;
        // wake up the student waiting for the biill
        notifyAll();
        ((Waiter) Thread.currentThread()).setWaiterState(WaiterState.RECEIVING_PAYMENT);
        //repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

        // wait while the bill is not paid
        while(!this.billIsPaid)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void informCompanion()
    {
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());
        students[sID].setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        //repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());
        
        this.studentSelectedCourses++;
        // Wake the student taking the order
        notifyAll();
        
        // Wait untill the course is served 
        while(!this.firstStudentJoinedTalk)
        {
            try 
            {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void prepareTheOrder()
    {
        ((Student) Thread.currentThread()).setStudentState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        //repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.studentSelectedCourses++;
    }
    
    public synchronized void joinTheTalk()
    {
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());
        students[sID].setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        //repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());

        this.firstStudentJoinedTalk = true;
        // Has to be waiting here 
        // for everybody to be served
        notifyAll();
    }

    public synchronized void hasEverbodyFinished()
    {
        ((Student) Thread.currentThread()).setStudentState(StudentState.CHATTING_WITH_COMPANIONS);

        while(!this.everyBodyFinished) 
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void startEating()
    {
        ((Student) Thread.currentThread()).setStudentState(StudentState.ENJOYING_THE_MEAL);
        int studentId = ((Student) Thread.currentThread ()).getID();
        //repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        // Simulate eating
        ((Student) Thread.currentThread()).studentEating();
    }

    public synchronized void endEating()
    {
        ((Student) Thread.currentThread()).setStudentState(StudentState.CHATTING_WITH_COMPANIONS);
        int studentId = ((Student) Thread.currentThread ()).getID();
        //repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        int sID = ((Student) Thread.currentThread()).getID();
        this.studentFinishedEating++;
        //repos.setNPortion(1);
        students[sID].setServedByWaiter(false);

        if(this.studentFinishedEating==Constants.students_number)
        {
            // means he was the last one
            ((Student) Thread.currentThread()).setLastStudent(true);
            System.out.printf("Student[%d] was last to eat\n", sID);
            this.everyBodyFinished = true;
            this.studentFinishedEating=0;
            notifyAll();
        }
        
    }

    public synchronized void honourTheBill()
    {
        // wait untill the bill is ready
        while(!this.billIsReady)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // after the waiting cycle, we pay it
        this.billIsPaid = true;
        notifyAll();
    }

    public synchronized void addUpOnesChoice()
    {
        ((Student) Thread.currentThread()).setStudentState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        //repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());

        
        while(this.studentSelectedCourses < Constants.students_number)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean hasEverybodyChosen()
    {
        if(this.studentSelectedCourses==Constants.students_number)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public synchronized void describeTheOrder()
    {
        ((Student) Thread.currentThread()).setStudentState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        //repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.orderDescribed = true;
        notifyAll();
    }

    public synchronized void waitingToBeServed(int sID)
    {
        // Add the student to the waiting to be served queue 
        try {
            queue.write(sID);
        } catch (MemException e1) {
            e1.printStackTrace();
        }
        this.studentWaiting++;
        // wake waiter waiting to serve 
        notifyAll();
        // block while it is not served
        while(!students[sID].servedByWaiter())
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.everyBodyFinished = false;
        this.studentServed++;
        //repos.setNPortion(1);
    }
}
