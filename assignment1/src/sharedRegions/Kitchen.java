package sharedRegions;

import entities.*;

public class Kitchen extends Thread 
{
    private int portionsDelivered;
    private int coursesDelievered;
    private boolean StartedPrep = false; 
    private boolean handedNoteToChef = false;
    private boolean portionReady = false;
    private boolean serviceDone = false;
    private boolean allPortionsDelivered = false;
    private boolean courseDone = false;
    private final GeneralRepos repos;   //references to general repository

    /**
	*  Kitchen instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Kitchen(GeneralRepos repos){this.repos = repos;}

    public synchronized void startPreparation()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.PREPARING_THE_COURSE);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.StartedPrep = true;
        notifyAll();
    }

    public synchronized void proceedToPresentation()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.DISHING_THE_PORTIONS);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.portionsDelivered=0;
    }

    public synchronized void haveNextPortionReady()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.DISHING_THE_PORTIONS);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.portionReady = true;
        notifyAll();
    }
    
    public synchronized void continuePreparation()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.PREPARING_THE_COURSE);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.courseDone = false;
    }

    public synchronized void cleanUp()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.CLOSING_SERVICE);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.serviceDone = true;
        notifyAll();
        // END
    }

    public synchronized boolean hasTheOrderBeenCompleted()
    {
        // Only when the 3 course meal has been delivered
        if(this.coursesDelievered==3)
        {
            return true;
        }
        else
            return false;
    }

    public synchronized boolean haveAllPortionsBeenDelivered()
    {
        if(this.portionsDelivered == 7)
        {
            this.coursesDelievered++;
            this.portionsDelivered=0;
            this.courseDone = true;
            notifyAll();
            return true;
        }
        else
        {
            return false;
        }
    }

    public synchronized void handNoteToTheChef()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.PLACING_THE_ORDER);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());

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

        while(!this.portionReady & !this.courseDone)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.portionReady = false;
        this.portionsDelivered++;
    }

    public synchronized void watchTheNews()
    {
        // This is a blocking state
        ((Chef) Thread.currentThread()).setState(ChefState.WAITING_FOR_AN_ORDER);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());

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
