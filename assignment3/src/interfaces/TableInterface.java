package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import commInfra.ReturnValue;

public interface TableInterface extends Remote
{

    public ReturnValue getThePad() throws RemoteException;
    

    public ReturnValue deliverPortion() throws RemoteException;
    

    /**
    *	 Method to verify if all Students have been served
    *    @return boolean that indicates true if all have been served or false if not
    */

    public ReturnValue haveAllClientsBeenServed() throws RemoteException;
    

    /**
    *	 Transitions the Waiter from the 'processing the bill' state to the 'receiving payment' state
    */

    public ReturnValue presentTheBill() throws RemoteException;
    

    /**
    *	 Transitions the Student to the 'chatting with companions' state
    */

    public ReturnValue informCompanion() throws RemoteException;
    

    /**
    *	 Transitions the Student from the 'selecting the courses' state to the 'organizing the order' state
    */

    public ReturnValue prepareTheOrder() throws RemoteException;
    
    
    /**
    *	 Transitions the Student from the 'organizing the order' state to the 'chatting with companions' state
    */

    public ReturnValue joinTheTalk() throws RemoteException;
    

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'chatting with companions' state
    */

    public ReturnValue hasEverbodyFinished() throws RemoteException;
    

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'enjoying the meal' state
    */

    public ReturnValue startEating() throws RemoteException;
    

    /**
    *	 Transitions the Student from the 'enjoying the meal' state to the 'chatting with companions' state
    */

    public ReturnValue endEating() throws RemoteException;
    

    public ReturnValue honourTheBill() throws RemoteException;
    

    /**
    *	 Transitions the Student to the 'organizing the order' state
    */

    public ReturnValue addUpOnesChoice() throws RemoteException;
    

    public ReturnValue hasEverybodyChosen() throws RemoteException;
    

    /**
    *	 Transitions the Student the 'describing the order' state
    */

    public ReturnValue describeTheOrder() throws RemoteException;
    

    public ReturnValue waitingToBeServed(int sID) throws RemoteException;
    

    /**
    *
    *Method called to shutdown the Table server
    *
    */

    public void shutdown() throws RemoteException;
    
}
