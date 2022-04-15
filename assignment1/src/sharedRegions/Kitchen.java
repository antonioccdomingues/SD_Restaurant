package sharedRegions;

import entities.*;

public class Kitchen extends Thread 
{
    private int portionsDelivered;
    private boolean StartedPrep = false; 
    private boolean handedNoteToChef = false;
    private boolean portionReady = false;
    private boolean serviceDone = false;
    private boolean allPortionsDelivered = false;

    public Kitchen(){}

    public synchronized void startPreparation()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.PREPARING_THE_COURSE);
        this.StartedPrep = true;
        notifyAll();
    }

    public synchronized void proceedToPresentation()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.DISHING_THE_PORTIONS);
    }

    public synchronized void haveNextPortionReady()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.DISHING_THE_PORTIONS);
        this.portionReady = true;
        notifyAll();
    }
    
    public synchronized void continuePreparation()
    {
        this.portionsDelivered=0;
    }

    public synchronized void cleanUp()
    {
        this.serviceDone = true;
        notifyAll();
        // END
    }

    public synchronized boolean hasTheOrderBeenCompleted()
    {
        // Only when the 3 course meal has been delivered
        return true;
    }

    public synchronized boolean haveAllPortionsBeenDelivered()
    {
        if(this.portionsDelivered < 7)
            return false;
        else
            return true;
    }

    public synchronized void handNoteToTheChef()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.PLACING_THE_ORDER);

        this.handedNoteToChef = true;

        notifyAll();

        while(!this.StartedPrep)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void collectPortion()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.WAITING_FOR_PORTION);

        notifyAll();

        if(!this.serviceDone)
        {
            while(!this.portionReady)
            {
                try {
                    wait(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        this.portionReady = false;
        this.portionsDelivered++;
    }

    public synchronized void watchTheNews()
    {
        // This is a blocking state
        ((Chef) Thread.currentThread()).setState(ChefState.WAITING_FOR_AN_ORDER);

        notifyAll();

        while(!handedNoteToChef)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
