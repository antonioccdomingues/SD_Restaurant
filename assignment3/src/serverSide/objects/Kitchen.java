package serverSide.objects;

import commInfra.*;
import clientSide.entities.WaiterState;
import clientSide.entities.ChefState;

import java.rmi.RemoteException;

import genclass.GenericIO;

import interfaces.GeneralReposInterface;
import serverSide.main.Constants;
import serverSide.main.BarMain;
import interfaces.*;


/**
 *    Kitchen.
 *
 *    It is responsible for the the synchronization of the Waiter and Chef
 *    and is implemented as an implicit monitor.
 *    
 */

public class Kitchen implements KitchenInterface 
{
    private int portionsDelivered;
    private int coursesDelievered=0;
    private int nEntities=0;
    private boolean StartedPrep = false; 
    private boolean handedNoteToChef = false;
    private boolean portionCollected = false;
    private boolean orderDone = false;
    private boolean notifyWaiter = false;
    private final GeneralReposInterface repos;   //references to general repository

    /**
	*  Kitchen instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Kitchen(GeneralReposInterface repos)
    {
        this.repos = repos;
    }

    public synchronized ReturnValue startPreparation() throws RemoteException
    {
        repos.setChefState(ChefState.PREPARING_THE_COURSE);
        this.StartedPrep = true;
        notifyAll();
        return new ReturnValue(false, ChefState.PREPARING_THE_COURSE, 0);
    }

    public synchronized ReturnValue proceedToPresentation() throws RemoteException
    {
        repos.setChefState(ChefState.DISHING_THE_PORTIONS);
        this.portionsDelivered = 0;
        return new ReturnValue(false, ChefState.DISHING_THE_PORTIONS, 0);
    }

    public synchronized ReturnValue haveNextPortionReady() throws RemoteException
    {
        repos.setChefState(ChefState.DISHING_THE_PORTIONS);
        this.portionCollected = false;
        return new ReturnValue(false, ChefState.DISHING_THE_PORTIONS, 0);
    }
    
    public synchronized ReturnValue continuePreparation() throws RemoteException
    {
        repos.setChefState(ChefState.PREPARING_THE_COURSE);
        this.coursesDelievered++;
        if(this.coursesDelievered==(Constants.courses_number-1))
        {
            this.orderDone = true;
        }

        return new ReturnValue(false, ChefState.PREPARING_THE_COURSE, 0);
    }

    public synchronized ReturnValue cleanUp() throws RemoteException
    {
        repos.setChefState(ChefState.CLOSING_SERVICE);
        // END
        return new ReturnValue(false, ChefState.CLOSING_SERVICE, 0);
    }

    public synchronized ReturnValue hasTheOrderBeenCompleted() throws RemoteException
    {
        // Only when the 3 course meal has been delivered
        return new ReturnValue(this.orderDone, 0, 0);
    }

    public synchronized ReturnValue haveAllPortionsBeenDelivered() throws RemoteException
    {
        //System.out.println(this.portionsDelivered);
        if(this.portionsDelivered == (Constants.students_number-1))
        {
            return new ReturnValue(true, 0, 0);
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
            return new ReturnValue(false, 0, 0);
        }
    }

    public synchronized ReturnValue handNoteToTheChef() throws RemoteException
    {
        repos.setWaiterState(WaiterState.PLACING_THE_ORDER);

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
        return new ReturnValue(false, WaiterState.PLACING_THE_ORDER, 0);
    }

    public synchronized ReturnValue collectPortion() throws RemoteException
    {
        this.portionCollected = true;
        notifyAll();
        return new ReturnValue(false, WaiterState.WAITING_FOR_PORTION, 0);
    }

    public synchronized ReturnValue watchTheNews() throws RemoteException
    {
        // This is a blocking state
        repos.setChefState(ChefState.WAITING_FOR_AN_ORDER);

        while(!this.handedNoteToChef)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, ChefState.WAITING_FOR_AN_ORDER, 0);
    }

    public synchronized ReturnValue haveAllClientsBeenServed() throws RemoteException
    {
        repos.setWaiterState(WaiterState.WAITING_FOR_PORTION);
        
        if(this.portionsDelivered==(Constants.students_number))
        {
            return new ReturnValue(true, WaiterState.WAITING_FOR_PORTION, 0);
        }
        else
        {
            return new ReturnValue(false, WaiterState.WAITING_FOR_PORTION, 0);
        }

    }

    public synchronized void shutdown () throws RemoteException
    {
        nEntities += 1;
        if (nEntities >= Constants.E_Kitchen) {
        	
        	try
        	{ repos.shutdown();
        	}
        	catch (RemoteException e)
        	{ GenericIO.writelnString ("Customer generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
	          System.exit (1);
        	}
        	BarMain.shutdown ();
        }
        notifyAll ();                                       // the barber may now terminate
    }
}
