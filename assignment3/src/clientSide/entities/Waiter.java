package clientSide.entities;

import java.rmi.RemoteException;
import genclass.*;
import commInfra.ReturnValue;
import clientSide.main.Constants;
import interfaces.*;

    /**
    *       Waiter Thread
    *
    *       Class used to simulate a Waiter's life cycle   
    *    
    */
public class Waiter extends Thread
{
    private int waiterID;
    /**
    *       Waiter State
    */
    private int state;
    /**
    *       Reference to the kitchen
    */
    private KitchenInterface kitchen;
    /**
    *       Flag to determine if the waiter can close the restaurant or not
    */
    private boolean canGoHome = false;
    /**
    *       Reference to the Bar
    */
    private BarInterface bar;
    /**
    *       Reference to the Table
    */
    private TableInterface table;

    /**
     *   Instantiation of a Waiter thread.
     *
     *     @param waiterID Waiter id
     *     @param state State
     *     @param kitchen reference to the Kitchen
     *     @param bar reference to the Bar
     *     @param table reference to the table
     */

    public Waiter(int waiterID, int state, KitchenInterface kitchen, BarInterface bar, TableInterface table)
    {
        this.setWaiterID(waiterID);
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
        this.table = table;
    }

    /** 
     * Get waiter id
     * @return int
     */

    public int getWaiterID() {
        return waiterID;
    }

    /** 
     * Set Waiter id
     */

    public void setWaiterID(int waiterID) {
        this.waiterID = waiterID;
    }

    /** 
     * canGoHome
     * @return boolean saying if can go home or not
     */

    public boolean CanGoHome() {
        return canGoHome;
    }

    public void setCanGoHome() {
        this.canGoHome = true;
    }

    /** 
     * Set Waiter state
     */

    public void setState(int state)
    {
        this.state = state;
    }

    /** 
     * Get Waiter State
     * @return int
     */

    public int getWaiterState()
    {
        return this.state;
    }

    /**
     *   Life cycle of the Waiter.
     *   
     *   Starts at the state appraisingSituation
     *   Ends when all the N Students left the restaurant
     */

    @Override
    public void run()
    {
        int action;

        while(!this.CanGoHome())
        {
            action = lookAround();
            switch(action)
            {
                case 0:
                    saluteTheClient();
                    returningToTheBar();
                    break;
                case 1:
                    getThePad();
                    handNoteToTheChef();
                    returningToTheBar();
                    break;
                case 2:
                    if(!haveAllClientsBeenServed())
                    {
                        collectPortion();
                        deliverPortion();
                    }
                    returningToTheBar();
                    break;
                case 3:
                    prepareTheBill();
                    presentTheBill();
                    returningToTheBar();
                    break;
                case 4:
                    sayGoodbye();
                    break;
            }
        }
        System.out.println("Waiter fechou o estabelecimento");
        // shutdown();
        // shutdown();
        // shutdown();
        GenericIO.writelnString("\033[41m Waiter End Of Life \033[0m");
    }

    /**
     * transitions the Waiter to appraising situation state
     *     
     */

    private int lookAround() {
    	ReturnValue ret = null;
    	try
        { ret = bar.lookAround();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
        return ret.getWaiterAction();           //NÃO ESQUECER DE IMPLEMENTAR ESTE MÉTODO
    }

    /**
     * transitions the Waiter from the 'appraising situation' state to the 'presenting the menu' state 
     *     
     */

    private void saluteTheClient() {
    	ReturnValue ret = null;
    	try
        { ret = bar.saluteTheClient();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Waiter from the 'presenting the menu' state to the 'appraising situation' state 
     *     
     */

    private void returningToTheBar() {
    	ReturnValue ret = null;
    	try
        { ret = bar.returningToTheBar();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Waiter from the 'appraising situation' state to the 'taking the order' state 
     *     
     */

    private void getThePad() {
    	ReturnValue ret = null;
    	try
        { ret = table.getThePad();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Waiter from the 'taking the order' state to the 'placing the order' state 
     *     
     */

    private void handNoteToTheChef() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.handNoteToTheChef();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * Returns tru if all clients have been served
     *     
     */

    private boolean haveAllClientsBeenServed() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.haveAllClientsBeenServed();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
        return ret.getBooleanValue();
    }

    /**
     * transitions the Waiter from the 'appraising situation' state to the 'waiting for portion' state 
     *     
     */

    private void collectPortion() {
    	ReturnValue ret = null;
    	try
        { ret = kitchen.collectPortion();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * Waiter delivers the portion and stays at the same state "Waiting for portion"
     *     
     */

    private void deliverPortion() {
    	ReturnValue ret = null;
    	try
        { ret = table.deliverPortion();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Waiter from the 'appraising situation' state to the 'processing the bill' state 
     *     
     */

    private void prepareTheBill() {
    	ReturnValue ret = null;
    	try
        { ret = bar.prepareTheBill();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Waiter from the 'processing the bill' state to the 'receiving payment' state 
     *     
     */

    private void presentTheBill() {
    	ReturnValue ret = null;
    	try
        { ret = table.presentTheBill();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * Waiter salutes the students and stays in appraising situation state
     *     
     */

    private void sayGoodbye() {
    	ReturnValue ret = null;
    	try
        { ret = bar.sayGoodbye();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
        this.setCanGoHome();
    }
}
