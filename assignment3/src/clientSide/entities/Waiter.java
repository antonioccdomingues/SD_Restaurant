package clientSide.entities;

import clientSide.stubs.*;
import genclass.*;

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
    private KitchenStub kitchen;
    /**
    *       Flag to determine if the waiter can close the restaurant or not
    */
    private boolean canGoHome = false;
    /**
    *       Reference to the Bar
    */
    private BarStub bar;
    /**
    *       Reference to the Table
    */
    private TableStub table;

    /**
     *   Instantiation of a Waiter thread.
     *
     *     @param waiterID Waiter id
     *     @param state State
     *     @param kitchen reference to the Kitchen
     *     @param bar reference to the Bar
     *     @param table reference to the table
     */

    public Waiter(int waiterID, int state, KitchenStub kitchen, BarStub bar, TableStub table)
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
            action = bar.lookAround();
            switch(action)
            {
                case 0:
                    bar.saluteTheClient();
                    bar.returningToTheBar();
                    break;
                case 1:
                    table.getThePad();
                    kitchen.handNoteToTheChef();
                    bar.returningToTheBar();
                    break;
                case 2:
                    if(!kitchen.haveAllClientsBeenServed())
                    {
                        kitchen.collectPortion();
                        table.deliverPortion();
                    }
                    bar.returningToTheBar();
                    break;
                case 3:
                    bar.prepareTheBill();
                    table.presentTheBill();
                    bar.returningToTheBar();
                    break;
                case 4:
                    bar.sayGoodbye();
                    break;
            }
        }
        System.out.println("Waiter fechou o estabelecimento");
        kitchen.shutdown();
        table.shutdown();
        bar.shutdown();
        GenericIO.writelnString("\033[41m Waiter End Of Life \033[0m");
    }
}
