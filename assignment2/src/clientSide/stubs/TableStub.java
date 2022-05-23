package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;

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

    /**
    *	 Transitions the Waiter from the 'appraising situation' state to the 'taking the order' state
    */

    public synchronized void getThePad()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

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

        w.setState((clientSide.entities.WaiterState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    public synchronized void deliverPortion()
    {
    }

    /**
    *	 Method to verify if all Students have been served
    *    @return boolean that indicates true if all have been served or false if not
    */

    public synchronized boolean haveAllClientsBeenServed()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

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

        w.setState((clientSide.entities.WaiterState) m_fromServer.getStateFields()[1]);
        boolean result = (Boolean) m_fromServer.getReturnValue();
        
        com.close ();
        return result;
    }

    /**
    *	 Transitions the Waiter from the 'processing the bill' state to the 'receiving payment' state
    */

    public synchronized void presentTheBill()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

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

        w.setState((clientSide.entities.WaiterState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student to the 'chatting with companions' state
    */

    public synchronized void informCompanion()
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
       
        s.setState((clientSide.entities.StudentState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'selecting the courses' state to the 'organizing the order' state
    */

    public synchronized void prepareTheOrder()
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
       
        s.setState((clientSide.entities.StudentState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }
    
    /**
    *	 Transitions the Student from the 'organizing the order' state to the 'chatting with companions' state
    */

    public synchronized void joinTheTalk()
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
       
        s.setState((clientSide.entities.StudentState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'chatting with companions' state
    */

    public synchronized void hasEverbodyFinished()
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
       
        s.setState((clientSide.entities.StudentState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'chatting with companions' state to the 'enjoying the meal' state
    */

    public synchronized void startEating()
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
       
        s.setState((clientSide.entities.StudentState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Student from the 'enjoying the meal' state to the 'chatting with companions' state
    */

    public synchronized void endEating()
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
       
        s.setState((clientSide.entities.StudentState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    public synchronized void honourTheBill()
    {
        //NÃO SEI SE SÃO NECESSÁRIAS. SE FOREM, N SEI BEM COMO IMPLEMENTAR!!!
    }

    /**
    *	 Transitions the Student to the 'organizing the order' state
    */

    public synchronized void addUpOnesChoice()
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
       
        s.setState((clientSide.entities.StudentState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    public synchronized boolean hasEverybodyChosen()
    {
        //NÃO SEI SE SÃO NECESSÁRIAS. SE FOREM, N SEI BEM COMO IMPLEMENTAR!!!
        return true;
    }

    /**
    *	 Transitions the Student the 'describing the order' state
    */

    public synchronized void describeTheOrder()
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
       
        s.setState((clientSide.entities.StudentState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    public synchronized void waitingToBeServed(int sID)
    {
        //NÃO SEI SE SÃO NECESSÁRIAS. SE FOREM, N SEI BEM COMO IMPLEMENTAR!!!
    }

    /**
    *
    *Method called to shutdown the Table server
    *
    */

    public void shutdown()
    {
        CommunicationChannel com = new CommunicationChannel (serverHostName, serverPortNumb);
        Object[] params = new Object[0];
        Object[] state_fields = new Object[0];
     
        Message m_toServer = new Message(24, params, 0, state_fields, 0, null);                                                          
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
