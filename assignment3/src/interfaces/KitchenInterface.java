package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import commInfra.ReturnValue;

public interface KitchenInterface extends Remote 
{

    public void startPreparation() throws RemoteException;
    

    /**
    *	 Transitions the Chef from the 'preparing a course' state to the 'dishing the portions' state
    */

    public void proceedToPresentation() throws RemoteException;
    

    /**
    *	 Transitions the Chef from the 'delivering the portions' state to the 'dishing the portions' state
    */

    public void haveNextPortionReady() throws RemoteException;
    

    /**
    *	 Transitions the Chef from the 'delivering the portions' state to the 'preparing a course' state
    */
    
    public void continuePreparation() throws RemoteException;
    

    /**
    *	 Transitions the Chef from the 'delivering the portions' state to the 'closing service' state
    */

    public void cleanUp() throws RemoteException;
    

    //NÃO SEI SE SÃO NECESSÁRIAS. SE FOREM, N SEI BEM COMO IMPLEMENTAR!!!

    public ReturnValue hasTheOrderBeenCompleted() throws RemoteException;
    

    public ReturnValue haveAllPortionsBeenDelivered() throws RemoteException;
    

    /**
    *	 Transitions the Waiter from the 'taking the order' state to the 'placing the order' state
    */

    public void handNoteToTheChef() throws RemoteException;
    

    /**
    *	 Transitions the Waiter from the 'appraising situation' state to the 'waiting for portion' state
    */

    public void collectPortion() throws RemoteException;
    

    /**
    *	 Chef Watching the news state
    */

    public void watchTheNews() throws RemoteException;
    

    /**
    *	 Transitions the Waiter to the 'waiting for portion' state
    *     @return boolean that indicates if all clients were served
    */

    public ReturnValue haveAllClientsBeenServed() throws RemoteException;
    

    /**
    *
    *Method called to shutdown the Kitchen server
    *
    */

    public void shutdown() throws RemoteException;
}
