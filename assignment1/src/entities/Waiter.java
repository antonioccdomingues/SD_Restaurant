package entities;

import sharedRegions.*;
import genclass.*;

public class Waiter extends Thread
{
    private WaiterState state;
    private Kitchen kitchen;
    private Bar bar;
    private Table table;
    private boolean canGoHome = false;

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
