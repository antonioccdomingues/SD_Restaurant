package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class TableStub 
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

    public TableStub (String hostName, int port)
    {
       serverHostName = hostName;
       serverPortNumb = port;
    }

    public synchronized void getThePad()
    {
    }

    public synchronized void deliverPortion()
    {
    }

    public synchronized boolean haveAllClientsBeenServed()
    {
        return true;
    }

    public synchronized void presentTheBill()
    {
    }

    public synchronized void informCompanion()
    {
    }

    public synchronized void prepareTheOrder()
    {
    }
    
    public synchronized void joinTheTalk()
    {
    }

    public synchronized void hasEverbodyFinished()
    {
    }

    public synchronized void startEating()
    {
    }

    public synchronized void endEating()
    {
    }

    public synchronized void honourTheBill()
    {
    }

    public synchronized void addUpOnesChoice()
    {
    }

    public synchronized boolean hasEverybodyChosen()
    {
        return true;
    }

    public synchronized void describeTheOrder()
    {
    }

    public synchronized void waitingToBeServed(int sID)
    {
    }
}
