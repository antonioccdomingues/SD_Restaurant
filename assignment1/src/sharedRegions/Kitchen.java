package sharedRegions;

import entities.*;
import genclass.*;

public class Kitchen extends Thread 
{
    public Kitchen(){}

    public synchronized void startPreparation()
    {
    }

    public synchronized void proceedToPresentation()
    {
    }

    public synchronized void haveNextPortionReady()
    {
    }
    
    public synchronized void continuePreparation()
    {
    }

    public synchronized void cleanUp()
    {
    }

    public synchronized boolean hasTheOrderBeenCompleted()
    {
        return true;
    }

    public synchronized boolean haveAllPortionsBeenDelivered()
    {
        return true;
    }

    public synchronized void handNoteToTheChef()
    {
    }

    public synchronized void collectPortion()
    {
    }
}
