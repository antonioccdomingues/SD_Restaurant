package entities;

public class Waiter extends Thread
{
    private WaiterState state;

    public Waiter(WaiterState state)
    {
        this.state = state;
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
