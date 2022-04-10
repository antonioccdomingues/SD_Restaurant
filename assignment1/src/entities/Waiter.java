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

    }
}
