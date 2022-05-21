package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class KitchenStub 
{
	/**
    *  Name of the computational system where the server is located.
    */

    private String serverHostName;

    /**
    *  Number of the listening port at the computational system where the server is located.
    */

    private int serverPortNumb;

    /**
    *  Instantiation of a remote reference
    *
    *    @param hostName name of the computational system where the server is located
    *    @param port number of the listening port at the computational system where the server is located
    */

    public KitchenStub(String hostName, int port)
    {
       serverHostName = hostName;
       serverPortNumb = port;
    }

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

    public synchronized void watchTheNews()
    {
    }

    public synchronized boolean haveAllClientsBeenServed()
    {
        return true;
    }
}
