package clientSide.entities;

import genclass.*;
import clientSide.stubs.BarStub;
import clientSide.stubs.KitchenStub;

/**
 *   Chef thread.
 *
 *   Used to simulate the Chef life cycle.
 */

public class Chef extends Thread
{
    /**
	 *  Chef identification.
	 */

    private int chefID;

    /**
	 *  Chef State.
	 */

    private int state;

    /**
	 *  Reference to the bar
	 */

    private BarStub bar;

    /**
	 *  Reference to the Kitchen
	 */

    private KitchenStub kitchen;


    /**
     *   Instantiation of a Chef thread.
     *
     *     @param chefID Chef id
     *     @param state State
     *     @param kitchen reference to the Kitchen
     *     @param bar reference to the Bar
     */

    public Chef(int chefID, int state, KitchenStub kitchen, BarStub bar)
    {
        this.setChefID(chefID);
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
    }

    
    /** 
     * Get chef id
     * @return int
     */
    public int getChefID() {
        return chefID;
    }

    
    /** set ChefId
     * @param chefID
     */
    public void setChefID(int chefID) {
        this.chefID = chefID;
    }

    
    /** 
     * Set Student State
     * @param state
     */
    public void setState(int state)
    {
        this.state = state;
    }

    
    /** 
     * Getter for ChefState
     * @return int
     */
    public int getChefState()
    {
        return this.state;
    }

    /**
     *   Life cycle of the Chef.
     *   
     *   Starts at the state watchingTheNews    
     *   Ends when all the portions have been deliveredk
     */

    @Override
    public void run()
    {
        boolean firstCourse = true;
        kitchen.watchTheNews();
        kitchen.startPreparation();

        do
        {
            if(!firstCourse)
            {
                kitchen.continuePreparation();
            }
            else
            {
                firstCourse = false;
            }
            kitchen.proceedToPresentation();
            bar.alertTheWaiter();

            while(!kitchen.haveAllPortionsBeenDelivered())
            {
                kitchen.haveNextPortionReady();
                bar.alertTheWaiter();
            }
        }while(!kitchen.hasTheOrderBeenCompleted());
        kitchen.cleanUp();
        GenericIO.writelnString("\033[41m Chef End Of Life \033[0m");
    }
}
