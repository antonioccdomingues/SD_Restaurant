package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

public class BarStub 
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

    public BarStub(String hostName, int port)
    {
       serverHostName = hostName;
       serverPortNumb = port;
    }

    public synchronized void saluteTheClient()
    {
    }

    public synchronized void alertTheWaiter()
    {
    }

    public synchronized void returningToTheBar()
    {
    }

    public synchronized void prepareTheBill()
    {
    }

    public synchronized int lookAround()
    {
        return 0;
    }

    public synchronized void sayGoodbye()
    {
    }

    public synchronized void signalTheWaiter(int sID)
    {
    }

    public synchronized void callTheWaiter()
    {
    }

    public synchronized void enter()
    {
    }

    public synchronized boolean FirstStudent(int sID)
    {
        return true;
    }

    public synchronized void exit()
    {
    }

    public synchronized boolean shouldHaveArrivedEarlier(int sID)
    {
        return true;
    }

    public synchronized void readTheMenu()
    {
    }
}
