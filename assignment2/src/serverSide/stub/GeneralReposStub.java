package serverSide.stub;

import java.util.Objects;

import commInfra.Message;
import commInfra.CommunicationChannel;
import genclass.GenericIO;
import genclass.TextFile;
import serverSide.entities.*;


public class GeneralReposStub 
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

    public GeneralReposStub (String hostName, int port)
    {
       serverHostName = hostName;
       serverPortNumb = port;
    }
    
    /**
	 *   Set waiter state.
	 *
	 *     @param state waiter state
	 */

	public synchronized void setWaiterState (WaiterState state)
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = state;
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
	}

    /**
	 *   Set student state.
	 *
	 *     @param id student id
	 *     @param state student state
	 */

    public synchronized void setStudentState (int id, StudentState state)
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = state;
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
    }

     /**
	 *   Set chef state.
	 *
	 *     @param state chef state
	 */

	public synchronized void setChefState (ChefState state)
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = state;
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
	}

    /**
	 *   Set Ncourse number.
	 *
	 *     @param number number to add to Ncourse
	 */

	public synchronized void setNCourse (int number)
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = number;
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
    }

    /**
	 *   Set Nportion number.
	 *
	 *     @param number number to add to Ncourse
	 */

	public synchronized void setNPortion (int number)
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = number;
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
    }

    /**
	 *   Set passenger state.
	 *
	 *     @param id passenger id
	 */

	public synchronized void setStudentsOrder (int id)
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = id;
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
	}

    private void reportInitialStatus ()
   {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[0];
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
   }


   //WORK TO DO 
    private void reportStatus ()
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[0];
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
    }

    /**
	 *   Write a specific state line at the end of the logging file, for example an message informing that
	 *   the plane has arrived.
	 *
	 *     @param message message to write in the logging file
	 */

	public synchronized void reportSpecificStatus (String message){
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[1];
    	Object[] state_fields = new Object[0];
    	params[0] = message;
    	
        Message m_toServer = new Message(16, params, 1, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
        
        com.close ();                                  
	}
}


