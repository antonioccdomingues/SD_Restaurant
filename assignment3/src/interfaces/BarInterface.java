package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import commInfra.ReturnValue;

/**
 *  Operational interface of a remote object of type Bar.
 *
 *    It instantiates a remote reference to the Bar.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public interface BarInterface extends Remote 
{

    /**
    *	 Transitions the Waiter from the 'appraising situation' state to the 'presenting the menu' state
    */

    public void saluteTheClient() throws RemoteException;

    /**
    *	 Transitions the Chef from the 'dishing the portions' state to the 'delivering portions' state
    */

    public void alertTheWaiter() throws RemoteException;

    /**
    *	 Transitions the Student from the 'waiting for portion' state to the 'appraising situation' state
    */

    public void returningToTheBar() throws RemoteException;

    /**
    *	 Transitions the Student from the 'appraising situation' state to the 'processing the bill' state
    */

    public void prepareTheBill() throws RemoteException;

    /**
    *	 Waiter appraiseing situation
    *     @return integer that specifies what is happening
    */

    public ReturnValue lookAround() throws RemoteException;

    /**
    *	 Method to say godbye to the waiter 
    */

    public ReturnValue sayGoodbye() throws RemoteException;

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'enjoying the meal' state
    */

    public void signalTheWaiter(int sID) throws RemoteException;

    /**
    *	 Transitions the Student from the 'organizing the order' state to the 'organizing the order' state
    */

    public void callTheWaiter() throws RemoteException;

    /**
    *	 Transitions the Student from the 'going to the restaurant' state to the 'taking a seat at the table' state
    */

    public void enter() throws RemoteException;

    /**
    *	 Method that indicates if is the first student or not
    *     @return boolean that check if is the first student or not
    */

    public ReturnValue FirstStudent(int sID) throws RemoteException;

    /**
    *	 Transitions the Student to the 'going home' state
    */

    public void exit() throws RemoteException;

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'paying the bill' state
    */

    public ReturnValue shouldHaveArrivedEarlier(int sID) throws RemoteException;

    /**
    *	 Transitions the Student from the 'taking a seat at the table' state to the 'selecting the courses' state
    */

    public void readTheMenu() throws RemoteException;

    /**
    *
    *Method called to shutdown the Bar server
    *
    */

    public void shutdown() throws RemoteException;
}
