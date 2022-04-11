package sharedRegions;

import FIFO.MemException;
import FIFO.MemFIFO;
import entities.*;
import genclass.*;
import main.*;

public class Table extends Thread
{
    private int firstStudentID;
    private int lastStudentID;
    private boolean firstStudent = true;
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

    public synchronized void saluteTheClient()
    {
        //Dequeue the student from the fifo and salute him
        // so he can transition to the next state
    }
    public synchronized void getThePad()
    {
    }
    public synchronized void deliverPortion()
    {
    }
    public synchronized boolean haveAllClientsBeenServed()
    {
        return true;
    }
    public synchronized void presentTheBill()
    {
    }
    public synchronized void walkABit() throws InterruptedException
    {
        sleep((long) (3 + 1000 * Math.random()));
    }
    public synchronized void enter()
    {
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());
        if(firstStudent)
        {
            this.firstStudent = false;
            this.firstStudentID = sID;
            System.out.printf("First Student %d \n", this.firstStudentID);
        }
        students[sID].setState(StudentState.TAKING_A_SEAT_AT_THE_TABLE);

        // Wake waiter
        notifyAll();

        // Wait untill the student is saluted to sit at the table
        while(!students[sID].getSalutedByWaiter())
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void readTheMenu()
    {
    }
    public synchronized void informCompanion()
    {
    }
    public synchronized void prepareTheOrder()
    {
    }
    public synchronized void joinTheTalk()
    {
    }
    public synchronized boolean hasEverbodyFinished()
    {
        return true;
    }
    public synchronized void startEating()
    {
    }
    public synchronized void endEating()
    {
    }
    public synchronized void honourTheBill()
    {
    }
    public synchronized boolean shouldHaveArrivedEarlier(int sID)
    {
        return true; 
    }
    public synchronized void addUpOnesChoice()
    {
    }
    public synchronized boolean hasEverybodyChosen()
    {
        return true;
    }
    public synchronized void describeTheOrder()
    {
    }
    public synchronized void exit()
    {
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
}
