package entities;

public class Waiter 
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

    public WaiterState getState()
    {
        return this.state;
    }

    @Override
    public String toString() 
    {
        return this.state.toString(); 
    }
}
