package serverSide.sharedRegions;

import serverSide.entities.*;
import serverSide.main.Constants;

public class Kitchen extends Thread 
{
    private int portionsDelivered;
    private int coursesDelievered=0;
    private double delay = 0;
    private boolean StartedPrep = false; 
    private boolean handedNoteToChef = false;
    private boolean portionCollected = false;
    private boolean orderDone = false;
    private boolean notifyWaiter = false;
    private final GeneralRepos repos;   //references to general repository

    /**
	*  Kitchen instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Kitchen(GeneralRepos repos)
    {
        this.repos = repos;
        this.delay = 300 * Math.random();
    }

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
        this.portionsDelivered = 0;
    }

    public synchronized void haveNextPortionReady()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.DISHING_THE_PORTIONS);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.portionCollected = false;
    }
    
    public synchronized void continuePreparation()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.PREPARING_THE_COURSE);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        this.coursesDelievered++;
        if(this.coursesDelievered==(Constants.courses_number-1))
        {
            this.orderDone = true;
        }
    }

    public synchronized void cleanUp()
    {
        ((Chef) Thread.currentThread()).setState(ChefState.CLOSING_SERVICE);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());
        // END
    }

    public synchronized boolean hasTheOrderBeenCompleted()
    {
        // Only when the 3 course meal has been delivered
        return this.orderDone;
    }

    public synchronized boolean haveAllPortionsBeenDelivered()
    {
        //System.out.println(this.portionsDelivered);
        if(this.portionsDelivered == (Constants.students_number-1))
        {
            return true;
        }
        else
        {
           while(!this.portionCollected)
            {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.portionsDelivered++;
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

        this.portionCollected = true;
        notifyAll();
    }

    public synchronized void watchTheNews()
    {
        // This is a blocking state
        ((Chef) Thread.currentThread()).setState(ChefState.WAITING_FOR_AN_ORDER);
        repos.setChefState(((Chef) Thread.currentThread()).getChefState());

        while(!this.handedNoteToChef)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean haveAllClientsBeenServed()
    {
        ((Waiter) Thread.currentThread()).setState(WaiterState.WAITING_FOR_PORTION);
        repos.setWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        
        if(this.portionsDelivered==(Constants.students_number))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
