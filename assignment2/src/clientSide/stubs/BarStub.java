package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;

/**
 *  Stub to the Bar.
 *
 *    It instantiates a remote reference to the Bar.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

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

    /**
    *	 Transitions the Waiter from the 'appraising situation' state to the 'presenting the menu' state
    */

    public void saluteTheClient()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(0, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        w.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    
    }

    /**
    *	 Transitions the Chef from the 'dishing the portions' state to the 'delivering portions' state
    */

    public void alertTheWaiter()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(1, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'waiting for portion' state to the 'appraising situation' state
    */

    public void returningToTheBar()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(2, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        w.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'appraising situation' state to the 'processing the bill' state
    */

    public void prepareTheBill()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(3, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        w.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Waiter appraiseing situation
    *     @return integer that specifies what is happening
    */

    public int lookAround()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(4, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        w.setState((int) m_fromServer.getStateFields()[1]);
        int result = (int) m_fromServer.getReturnValue();
        com.close ();

        return result;
    }

    /**
    *	 Method to say godbye to the waiter 
    */

    public void sayGoodbye()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(5, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        w.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'enjoying the meal' state
    */

    public void signalTheWaiter(int sID)
    {
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[3];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	state_fields[2] = s.isLastStudent();
    	
        Message m_toServer = new Message(6, params, 0, state_fields, 3, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'organizing the order' state to the 'organizing the order' state
    */

    public void callTheWaiter()
    {
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(7, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'going to the restaurant' state to the 'taking a seat at the table' state
    */

    public void enter()
    {
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(8, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Method that indicates if is the first student or not
    *     @return boolean that check if is the first student or not
    */

    public boolean FirstStudent(int sID)
    {
        Student s = (Student) Thread.currentThread();
    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(9, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        boolean result = (Boolean) m_fromServer.getReturnValue();
        
        com.close ();
        return result;
    }

    /**
    *	 Transitions the Student to the 'going home' state
    */

    public void exit()
    {
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(10, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'paying the bill' state
    */

    public boolean shouldHaveArrivedEarlier(int sID)
    {
        Student s = (Student) Thread.currentThread();
    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(11, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        boolean result = (Boolean) m_fromServer.getReturnValue();
        
        com.close ();
        return result;
    }

    /**
    *	 Transitions the Student from the 'taking a seat at the table' state to the 'selecting the courses' state
    */

    public void readTheMenu()
    {
        Student s = (Student) Thread.currentThread();

    	CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
    	Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
    	state_fields[0] = s.getID();
    	state_fields[1] = s.getStudentState();
    	
        Message m_toServer = new Message(12, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 
       
        s.setState((int) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *
    *Method called to shutdown the Bar server
    *
    */

    public void shutdown()
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
        Object[] params = new Object[0];
        Object[] state_fields = new Object[0];
     
        Message m_toServer = new Message(99, params, 0, state_fields, 0, null);                                                          
        Message m_fromServer;            
        
        while (!com.open ())                                                      
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        com.close ();
    }
}
