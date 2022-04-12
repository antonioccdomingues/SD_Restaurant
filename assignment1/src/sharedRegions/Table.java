package sharedRegions;

import entities.*;
import genclass.*;
import java.util.ArrayList;

public class Table extends Thread
{
    private boolean everyStudentSelected = false;
    private int studentSelectedCourses;

    public Table()
    {
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
    public synchronized void readTheMenu()
    {
        ((Student) Thread.currentThread()).setState(StudentState.SELECTING_THE_COURSES);
        // Means he transitioned from previous state, having selected the course
        this.studentSelectedCourses++;

        // RETIRAR ISTO DAQUI NO FUTURO
        // RETIRAR ISTO DAQUI NO FUTURO
        // RETIRAR ISTO DAQUI NO FUTURO
        // RETIRAR ISTO DAQUI NO FUTURO
        // RETIRAR ISTO DAQUI NO FUTURO
        ((Student) Thread.currentThread()).setServedByWaiter();
    }
    public synchronized void informCompanion()
    {
        ((Student) Thread.currentThread()).setState(StudentState.CHATTING_WITH_COMPANIONS);

        notifyAll();
        
        boolean served = ((Student) Thread.currentThread()).servedByWaiter();
        // Wait untill the course is served 
        while(!served)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void prepareTheOrder()
    {
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);
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
        notifyAll();
        this.everyStudentSelected = true;
    }
    public synchronized void addUpOnesChoice()
    {
        int sID = ((Student) Thread.currentThread()).getID();
        ((Student) Thread.currentThread()).setState(StudentState.ORGANIZING_THE_ORDER);

        notifyAll();

        while(!this.everyStudentSelected)
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
        if(this.studentSelectedCourses==7)
        {
            this.everyStudentSelected = true;
            return true;
        }
        else
        {
            return false;
        }
    }
    public synchronized void describeTheOrder()
    {
    }
}
