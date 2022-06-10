package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GeneralReposInterface extends Remote 
{
    /**
	 *   Set waiter state.
	 *
	 *     @param state waiter state
	 */

	public void setWaiterState (int state) throws RemoteException;
    

    /**
	 *   Set student state.
	 *
	 *     @param id student id
	 *     @param state student state
	 */

    public void setStudentState (int id, int state) throws RemoteException;
    

     /**
	 *   Set chef state.
	 *
	 *     @param state chef state
	 */

	public void setChefState (int state) throws RemoteException;
    

    /**
	 *   Set Ncourse number.
	 *
	 *     @param number number to add to Ncourse
	 */

	public void setNCourse (int number) throws RemoteException;
    

    /**
	 *   Set Nportion number.
	 *
	 *     @param number number to add to Ncourse
	 */

	public void setNPortion (int number) throws RemoteException;
    

    /**
	 *   Set passenger state.
	 *
	 *     @param id passenger id
	 */

	public void setStudentsOrder(int id) throws RemoteException;
    
    /**
	 *   Write a specific state line at the end of the logging file, for example an message informing that
	 *   the plane has arrived.
	 *
	 *     @param message message to write in the logging file
	 */

	public void reportSpecificStatus (String message) throws RemoteException;

    /**
    *
    *Method called to shutdown the General Repository server
    *
    */

    public void shutdown() throws RemoteException;
    
}


