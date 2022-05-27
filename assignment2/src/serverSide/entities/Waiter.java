package serverSide.entities;

    /**
 *   Waiter thread.
 *
 *   Used to simulate the Waiter life cycle.
 */
public interface Waiter 
{

    /**
     *   Set Waiter id.
     *
     *     @param id Waiter id
     */

    public void setWaiterID(int id);

    /**
     *   Get waiter id.
     *
     *     @return Waiter Id.
     */

    public int getWaiterID();

    
    public boolean CanGoHome();

    public void setCanGoHome();

    /**
     *   Set Waiter state.
     *
     *     @param state Waiter state
     */
    public void setWaiterState(int state);

    /**
     *   Get Waiter state.
     *
     *     @return Waiter state.
     */
    public int getWaiterState();

}
