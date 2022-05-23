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

    public Waiter(int waiterID, int state, KitchenStub kitchen, BarStub bar, TableStub table)
    {
        this.setWaiterID(waiterID);
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
        this.table = table;
    }

    public int getWaiterID() {
        return waiterID;
    }

    public void setWaiterID(int waiterID) {
        this.waiterID = waiterID;
    }

    public boolean CanGoHome() {
        return canGoHome;
    }

    public void setCanGoHome() {
        this.canGoHome = true;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getWaiterState()
    {
        return this.state;
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
