package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;

public class KitchenStub 
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

    public KitchenStub(String hostName, int port)
    {
       serverHostName = hostName;
       serverPortNumb = port;
    }

    /**
    *	 Transitions the Chef from the 'waiting for an order' state to the 'preparing a course' state
    */

    public synchronized void startPreparation()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(28, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Chef from the 'preparing a course' state to the 'dishing the portions' state
    */

    public synchronized void proceedToPresentation()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(29, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Chef from the 'delivering the portions' state to the 'dishing the portions' state
    */

    public synchronized void haveNextPortionReady()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(30, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Chef from the 'delivering the portions' state to the 'preparing a course' state
    */
    
    public synchronized void continuePreparation()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(31, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Chef from the 'delivering the portions' state to the 'closing service' state
    */

    public synchronized void cleanUp()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(32, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    //NÃO SEI SE SÃO NECESSÁRIAS. SE FOREM, N SEI BEM COMO IMPLEMENTAR!!!

    public synchronized boolean hasTheOrderBeenCompleted()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(33, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        boolean result = (Boolean) m_fromServer.getReturnValue();
        com.close ();

        return result;
    }

    public synchronized boolean haveAllPortionsBeenDelivered()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(34, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        boolean result = (Boolean) m_fromServer.getReturnValue();
        com.close ();

        return result;
    }

    /**
    *	 Transitions the Waiter from the 'taking the order' state to the 'placing the order' state
    */

    public synchronized void handNoteToTheChef()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(35, params, 0, state_fields, 2, null);                                                          
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
    *	 Transitions the Waiter from the 'appraising situation' state to the 'waiting for portion' state
    */

    public synchronized void collectPortion()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(36, params, 0, state_fields, 2, null);                                                          
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
    *	 Chef Watching the news state
    */

    public synchronized void watchTheNews()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

        Message m_toServer = new Message(37, params, 0, state_fields, 2, null);                                                          
        Message m_fromServer;

        while (!com.open ())                                                      
        { try
          { Thread.currentThread ().sleep ((long) (10));
          }
          catch (InterruptedException e) {}
        }
        
        com.writeObject (m_toServer);
        
        m_fromServer = (Message) com.readObject();                 

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    /**
    *	 Transitions the Waiter to the 'waiting for portion' state
    *     @return boolean that indicates if all clients were served
    */

    public synchronized boolean haveAllClientsBeenServed()
    {
        Waiter w = (Waiter) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = w.getWaiterID();
    	state_fields[1] = w.getWaiterState();

        Message m_toServer = new Message(38, params, 0, state_fields, 2, null);                                                          
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
    *
    *Method called to shutdown the Kitchen server
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
