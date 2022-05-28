package serverSide.entities;

/**
 *   Chef thread.
 *
 *   Used to simulate the Chef life cycle.
 */

public interface Chef 
{
    /**
     *   Set Chef id.
     *
     *     @param id chef id
     */

    public void setChefID(int id);

    /**
     *   Get Chef id.
     *
     *     @return Chef Id.
     */

    public int getChefID();

    /**
     *   Set Chef state.
     *
     *     @param state Chef state
     */

    public void setChefState(int state);

    /**
     *   Get Chef state.
     *
     *     @return Chef state.
     */
    public int getChefState();

}
