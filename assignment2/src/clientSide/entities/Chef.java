package clientSide.entities;

import genclass.*;
import clientSide.stubs.BarStub;
import clientSide.stubs.KitchenStub;

public class Chef extends Thread
{
    private int chefID;
    private int state;
    private BarStub bar;
    private KitchenStub kitchen;

    public Chef(int chefID, int state, KitchenStub kitchen, BarStub bar)
    {
        this.setChefID(chefID);
        this.state = state;
        this.kitchen = kitchen;
        this.bar = bar;
    }

    public int getChefID() {
        return chefID;
    }

    public void setChefID(int chefID) {
        this.chefID = chefID;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getChefState()
    {
        return this.state;
    }

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
