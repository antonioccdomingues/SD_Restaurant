package sharedRegions;

import entities.*;
import main.*;
import FIFO.*;


public class Table extends Thread
{
    private boolean orderDescribed = false;
    private boolean informStudent = false;
    private boolean everyBodyFinished = false;
    private int studentSelectedCourses;
    private int studentServed = 0;
    private int studentFinishedEating;
    private final Student[] students;
    private MemFIFO<Integer> queue;

    public Table()
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

    public synchronized void getThePad()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.TAKING_THE_ORDER);

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
        notifyAll();

        // Dequeue the student
        try {
            student_served = queue.read();
        } catch (MemException e) 
        {
        }

        students[student_served].setServedByWaiter();
    }

    public synchronized boolean haveAllClientsBeenServed()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.WAITING_FOR_PORTION);
        if(this.studentServed==7)
        {
            return true;
        }
        else
            return false;
    }

    public synchronized void presentTheBill()
    {
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
        while(!students[sID].servedByWaiter())
        {
            try 
            {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.studentServed++;
        System.out.printf("[%d] has been served\n",sID);
    }

    public synchronized void prepareTheOrder()
    {
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);
        this.studentSelectedCourses++;
    }
    
    public synchronized void joinTheTalk()
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

        // Has to be waiting here 
        // for everybody to be served
        notifyAll();
        
        // Wait untill the course is served 
        while(!students[sID].servedByWaiter())
        {
            try 
            {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.studentServed++;
        System.out.printf("First student[%d] has been served\n",sID);
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

        // Simulate eating
        ((Student) Thread.currentThread()).studentEating();
    }

    public synchronized void endEating()
    {
        ((Student) Thread.currentThread()).setState(StudentState.CHATTING_WITH_COMPANIONS);
        this.studentFinishedEating++;
        if(this.studentFinishedEating==7)
        {
            this.everyBodyFinished = true;
            System.out.println("Everybody finished");
        }
        notifyAll();
    }

    public synchronized void honourTheBill()
    {
        notifyAll();
    }

    public synchronized void addUpOnesChoice()
    {
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);

        notifyAll();
        
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
        this.orderDescribed = true;
        notifyAll();
    }
}
