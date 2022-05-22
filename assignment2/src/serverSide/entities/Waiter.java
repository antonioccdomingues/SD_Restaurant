package serverSide.entities;

    /**
    *       Waiter Thread
    *
    *       Class used to simulate a Waiter's life cycle   
    *    
    */
public interface Waiter 
{

    public void setWaiterID(int id);

    public int getWaiterID();

    public boolean CanGoHome();

    public void setCanGoHome();

    public void setState(WaiterState state);

    public WaiterState getWaiterState();

}
