package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import commInfra.ReturnValue;

public interface TableInterface extends Remote
{

    public void getThePad() throws RemoteException;
    

    public void deliverPortion() throws RemoteException;
    

    /**
    *	 Method to verify if all Students have been served
    *    @return boolean that indicates true if all have been served or false if not
    */

    public ReturnValue haveAllClientsBeenServed() throws RemoteException;
    

    /**
    *	 Transitions the Waiter from the 'processing the bill' state to the 'receiving payment' state
    */

    public void presentTheBill() throws RemoteException;
    

    /**
    *	 Transitions the Student to the 'chatting with companions' state
    */

    public void informCompanion() throws RemoteException;
    

    /**
    *	 Transitions the Student from the 'selecting the courses' state to the 'organizing the order' state
    */

    public void prepareTheOrder() throws RemoteException;
    
    
    /**
    *	 Transitions the Student from the 'organizing the order' state to the 'chatting with companions' state
    */

    public void joinTheTalk() throws RemoteException;
    

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'chatting with companions' state
    */

    public void hasEverbodyFinished() throws RemoteException;
    

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'enjoying the meal' state
    */

    public void startEating() throws RemoteException;
    

    /**
    *	 Transitions the Student from the 'enjoying the meal' state to the 'chatting with companions' state
    */

    public void endEating() throws RemoteException;
    

    public void honourTheBill() throws RemoteException;
    

    /**
    *	 Transitions the Student to the 'organizing the order' state
    */

    public void addUpOnesChoice() throws RemoteException;
    

    public ReturnValue hasEverybodyChosen() throws RemoteException;
    

    /**
    *	 Transitions the Student the 'describing the order' state
    */

    public void describeTheOrder() throws RemoteException;
    

    public void waitingToBeServed(int sID) throws RemoteException;
    

    /**
    *
    *Method called to shutdown the Table server
    *
    */

    public void shutdown() throws RemoteException;
    
}
