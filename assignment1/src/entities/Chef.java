package entities;

import genclass.*;
import sharedRegions.Bar;
import sharedRegions.Kitchen;

public class Chef extends Thread
{
    private ChefState state;
    private Bar bar;
    private Kitchen kitchen;

    public Chef(ChefState state, Kitchen kitchen, Bar bar)
    {
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
    }

    public void setState(ChefState state)
    {
        this.state = state;
    }

    public ChefState getChefState()
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
        boolean firstCourse = true;
        bar.watchTheNews();
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
