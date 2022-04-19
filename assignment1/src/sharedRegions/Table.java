package sharedRegions;

import entities.*;
import main.*;
import FIFO.*;


public class Table extends Thread
{
    private boolean orderDescribed = false;
    private boolean informStudent = false;
    private boolean everyBodyFinished = false;
    private boolean firstStudentJoinedTalk = false;
    private boolean orderIsDone = false;
    private boolean billIsReady = false;
    private boolean billIsPaid = false;
    private int studentSelectedCourses;
    private int studentServed = 0;
    private int studentFinishedEating;
    private int coursesDelivered = 0;
    private final Student[] students;
    private MemFIFO<Integer> queue;
    private final GeneralRepos repos;   //references to general repository    

    /**
	*  Table instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Table(GeneralRepos repos)
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

    public synchronized void getThePad()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.TAKING_THE_ORDER);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        notifyAll();

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

        // Dequeue the student
        try {
            student_served = queue.read();
        } catch (MemException e) 
        {
        }

        students[student_served].setServedByWaiter(true);
        this.studentServed++;
        //repos.setNCourse(1);
        notifyAll();
    }

    public synchronized boolean haveAllClientsBeenServed()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.WAITING_FOR_PORTION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        if(this.studentServed==7)
        {
            this.studentServed=0;
            this.coursesDelivered++;
            //repos.setNCourse(1);
            if(this.coursesDelivered==3)
            {
                this.orderIsDone = true;
            }
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
        ((Waiter) Thread.currentThread()).setState(WaiterState.RECEIVING_PAYMENT);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

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
        students[sID].setState(StudentState.CHATTING_WITH_COMPANIONS);
        

        try {
            queue.write(sID);
        } catch (MemException e1) {
            e1.printStackTrace();
        }
        
        this.studentSelectedCourses++;
        this.informStudent=true;

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
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.studentSelectedCourses++;
    }
    
    public synchronized void joinTheTalk()
    {
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());
        students[sID].setState(StudentState.CHATTING_WITH_COMPANIONS);
        repos.setStudentState(sID, ((Student) Thread.currentThread()).getStudentState());

        try {
            queue.write(sID);
        } catch (MemException e1) {
            e1.printStackTrace();
        }

        this.firstStudentJoinedTalk = true;
        // Has to be waiting here 
        // for everybody to be served
        notifyAll();
    }

    public synchronized void hasEverbodyFinished()
    {
        ((Student) Thread.currentThread()).setState(StudentState.CHATTING_WITH_COMPANIONS);

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
        ((Student) Thread.currentThread()).setState(StudentState.ENJOYING_THE_MEAL);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        // Simulate eating
        ((Student) Thread.currentThread()).studentEating();
    }

    public synchronized void endEating()
    {
        ((Student) Thread.currentThread()).setState(StudentState.CHATTING_WITH_COMPANIONS);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        int sID = ((Student) Thread.currentThread()).getID();
        this.studentFinishedEating++;
        //repos.setNPortion(1);
        students[sID].setServedByWaiter(false);

        // Add the student which has finished eating, to the
        // waiting to be served queue
        try {
            queue.write(sID);
        } catch (MemException e1) {
            e1.printStackTrace();
        }

        if(this.studentFinishedEating==7)
        {
            // means he was the last one
            ((Student) Thread.currentThread()).lastStudent = true;
            this.everyBodyFinished = true;
            this.studentFinishedEating=0;
            //repos.setNPortion(10000);
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
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());

        
        while(!this.informStudent)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.informStudent = false;
    }

    public synchronized boolean hasEverybodyChosen()
    {
        if(this.studentSelectedCourses==7)
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
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);
        int studentId = ((Student) Thread.currentThread ()).getID();
        repos.setStudentState(studentId, ((Student) Thread.currentThread()).getStudentState());
        this.orderDescribed = true;
        notifyAll();
    }

    public synchronized void waitingToBeServed(int sID)
    {
        // block while it is not served
        while(!students[sID].servedByWaiter())
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean isOrderDone()
    {
        // Here we will check if the 3 courses have been eaten
        if(this.orderIsDone)
        {
            return true;
        }
        else
            return false;
    }
}
