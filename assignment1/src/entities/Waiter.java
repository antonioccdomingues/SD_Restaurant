package entities;

import sharedRegions.*;
import genclass.*;

    /**
    *       Waiter Thread
    *
    *       Class used to simulate a Waiter's life cycle   
    *    
    */
public class Waiter extends Thread
{
    /**
    *       Waiter State
    */
    private WaiterState state;
    /**
    *       Reference to the kitchen
    */
    private Kitchen kitchen;
    /**
    *       Flag to determine if the waiter can close the restaurant or not
    */
    private boolean canGoHome = false;
    /**
    *       Reference to the Bar
    */
    private Bar bar;
    /**
    *       Reference to the Table
    */
    private Table table;

    public Waiter(WaiterState state, Kitchen kitchen, Bar bar, Table table)
    {
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
        this.table = table;
    }

    public boolean CanGoHome() {
        return canGoHome;
    }

    public void setCanGoHome() {
        this.canGoHome = true;
    }

    public void setState(WaiterState state)
    {
        this.state = state;
    }

    public WaiterState getWaiterState()
    {
        return this.state;
    }

    @Override
    public String toString() 
    {
        return this.state.toString(); 
    }

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
        GenericIO.writelnString("\033[41m Waiter End Of Life \033[0m");
    }
}
