package sharedRegions;

import entities.*;
import genclass.*;

import javax.management.remote.SubjectDelegationPermission;
import javax.sound.sampled.SourceDataLine;

import FIFO.*;
import main.*;

public class Bar extends Thread
{
    private int firstStudentID;
    private int lastStudentID=-1;
    private int studentsSaluted=0;
    private int studentsEntered;
    private int studentsDone;
    private boolean allStudentsServed = false;
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
        // set the seat of the student upon being saluted
        students[student_saluted].setTableSeat(this.studentsSaluted);
        this.studentsSaluted++;
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
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);
    }

    public synchronized void prepareTheBill()
    {
    }

    public synchronized int lookAround()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.APPRAISING_SITUATION);

        if(this.studentsSaluted < 7)
        {
            // While the queue is empty, continue to look for the other students
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
        else if(this.studentsDone==7)
        {
            return 4;
        }
        return -1;
    }
    public synchronized boolean sayGoodbye()
    {
        // While we haven't said goodbye to all students
        while(!this.allStudentsServed)
        {
            try {
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

    public synchronized void signalTheWaiter()
    {
    }

    public synchronized void callTheWaiter()
    {
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
        students[sID].setState(StudentState.GOING_HOME);
        this.studentsDone++;
        System.out.printf("Student %d Exited \n", sID);
        if(this.studentsDone==7)
            // Change this variables name
            this.allStudentsServed = true;
        notifyAll();
    }
    public synchronized boolean shouldHaveArrivedEarlier(int sID)
    {
        if(this.lastStudentID == sID)
            return true; 
        else
            return false;
    }
}
