package sharedRegions;

import entities.*;
import genclass.*;
import FIFO.*;
import main.*;

public class Bar extends Thread
{
    private int firstStudentID;
    private int studentsServed=0;
    private boolean allStudentsServed = false;
    private int lastStudentID;
    private boolean firstStudent = true;
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

        notifyAll();
    }

    public synchronized void watchTheNews()
    {
    }

    public synchronized void alertTheWaiter()
    {
    }

    public synchronized void returningToTheBar()
    {
    }

    public synchronized void prepareTheBill()
    {
    }

    public synchronized int lookAround()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);

        if(this.studentsServed < 7)
        {
            // While the queue is empty, continue to look around
            while(this.queue.getN()==0)
            {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return 0;
        }
        else
        {
            return 3;
        }
    }

    public synchronized boolean sayGoodbye()
    {
        while(!this.allStudentsServed)
        {
            try {
                wait();
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public synchronized void signalTheWaiter()
    {
    }

    public synchronized void callTheWaiter()
    {
    }

    public synchronized void walkABit() 
    {
        try {
            sleep((long) (3 + 1000 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void enter()
    {
        int sID;
        sID = ((Student) Thread.currentThread()).getID();
        students[sID] =  ((Student) Thread.currentThread());

        try {
            queue.write(sID);
        } catch (MemException e1) {
            e1.printStackTrace();
        }

        if(firstStudent)
        {
            this.firstStudent = false;
            this.firstStudentID = sID;
            System.out.printf("First Student %d \n", this.firstStudentID);
        }

        // Update the state
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
        this.studentsServed++;
        System.out.printf("Student %d Was served\n", sID);
        if(this.studentsServed==7)
            this.allStudentsServed = true;
        notifyAll();
    }
}
