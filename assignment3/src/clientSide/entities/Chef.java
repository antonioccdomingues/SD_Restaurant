package clientSide.entities;

import java.rmi.RemoteException;

import commInfra.ReturnValue;
import interfaces.kitchenInterface;
import interfaces.barInterface;

/**
 *   Chef thread.
 *
 *   Used to simulate the Chef life cycle.
 */

public class Chef extends Thread
{
    /**
	 *  Chef identification.
	 */

    private int chefID;

    /**
	 *  Chef State.
	 */

    private int state;

    /**
	 *  Reference to the bar
	 */

    private BarInterface bar;

    /**
	 *  Reference to the Kitchen
	 */

    private KitchenInterface kitchen;


    /**
     *   Instantiation of a Chef thread.
     *
     *     @param chefID Chef id
     *     @param state State
     *     @param kitchen reference to the Kitchen
     *     @param bar reference to the Bar
     */

    public Chef(int chefID, int state, KitchenInterface kitchen, BarInterface bar)
    {
        this.setChefID(chefID);
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
    }

    
    /** 
     * Get chef id
     * @return int
     */
    public int getChefID() {
        return chefID;
    }

    
    /** set ChefId
     * @param chefID
     */
    public void setChefID(int chefID) {
        this.chefID = chefID;
    }

    
    /** 
     * Set Student State
     * @param state
     */
    public void setState(int state)
    {
        this.state = state;
    }

    
    /** 
     * Getter for ChefState
     * @return int
     */
    public int getChefState()
    {
        return this.state;
    }

    /**
     *   Life cycle of the Chef.
     *   
     *   Starts at the state watchingTheNews    
     *   Ends when all the portions have been deliveredk
     */

    @Override
    public void run()
    {
        boolean firstCourse = true;
        watchTheNews();
        startPreparation();

        do
        {
            if(!firstCourse)
            {
                continuePreparation();
            }
            else
            {
                firstCourse = false;
            }
            proceedToPresentation();
            alertTheWaiter();

            while(!haveAllPortionsBeenDelivered())
            {
                kitchen.haveNextPortionReady();
                bar.alertTheWaiter();
            }
        }while(!kitchen.hasTheOrderBeenCompleted());
        kitchen.cleanUp();
        GenericIO.writelnString("\033[41m Chef End Of Life \033[0m");
    }

    /**
     * transitions the Chef from the 'waiting for an order' state to the 'waiting for an order' state
     *     
     */
    
    private void watchTheNews() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.watchTheNews();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Chef from the 'waiting for an order' state to the 'preparing a course' state
     *     
     */

    private void startPreparation() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.startPreparation();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Chef from the 'delivereing the portions' state to the 'preparing a course' state
     *     
     */

    private void continuePreparation() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.continuePreparation();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Chef from the 'preparing a course' state to the 'dishing the portions' state
     *     
     */

    private void proceedToPresentation() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.proceedToPresentation();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Chef from the 'dishing the portions' state to the 'delivering the portions' state and wakes the waiter
     *     
     */

    private void alertTheWaiter() {
    	ReturnValue ret = null;
    	try
        { ret = bar.alertTheWaiter();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * sleeps while all portions haven't been delivered the 
     *     
     */

    private boolean haveAllPortionsBeenDelivered() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.haveAllPortionsBeenDelivered();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
        return ret.getBooleanValue();
    }
    
    /**
     * transitions the Chef from the 'delivering the portions' state to the 'dishing the portions' state
     *     
     */

    private void haveNextPortionReady() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.haveNextPortionReady();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * return true if the order is completed
     *     
     */

    private boolean hasTheOrderBeenCompleted() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.hasTheOrderBeenCompleted();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
        return ret.getBooleanValue();
    }

    /**
     * transitions the Chef from the 'delivering the portions' state to the 'closing service' state
     *     
     */

    private void cleanUp() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.cleanUp();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
}
