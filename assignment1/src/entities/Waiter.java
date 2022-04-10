package entities;

import sharedRegions.*;

public class Waiter extends Thread
{
    private WaiterState state;
    private Kitchen kitchen;
    private Bar bar;
    private Table table;

    public Waiter(WaiterState state, Kitchen kitchen, Bar bar, Table table)
    {
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
        this.table = table;
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
        while(!bar.sayGoodbye())
        {
            action = bar.lookAround();
            switch(action)
            {
                case 0:
                    table.saluteTheClient();
                    break;
                case 1:
                    table.getThePad();
                    kitchen.handNoteToTheChef();
                    break;
                case 2:
                    while(!table.haveAllClientsBeenServed())
                    {
                        kitchen.collectPortion();
                        table.deliverPortion();
                    }
                    break;
                case 3:
                    bar.prepareTheBill();
                    table.presentTheBill();
            }
            bar.returningToTheBar();
        }
    }
}
