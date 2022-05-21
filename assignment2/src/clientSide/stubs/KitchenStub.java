package clientSide.stubs;

import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

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

    public synchronized void startPreparation()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

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

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    public synchronized void proceedToPresentation()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

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

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    public synchronized void haveNextPortionReady()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

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

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }
    
    public synchronized void continuePreparation()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

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

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    public synchronized void cleanUp()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

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

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

    //NÃO SEI SE SÃO NECESSÁRIAS. SE FOREM, N SEI BEM COMO IMPLEMENTAR!!!

    public synchronized boolean hasTheOrderBeenCompleted()
    {
        //NÃO SEI SE SÃO NECESSÁRIAS. SE FOREM, N SEI BEM COMO IMPLEMENTAR!!!
        return true;
    }

    public synchronized boolean haveAllPortionsBeenDelivered()
    {
        //NÃO SEI SE SÃO NECESSÁRIAS. SE FOREM, N SEI BEM COMO IMPLEMENTAR!!!
        return true;
    }

    public synchronized void handNoteToTheChef()
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

    public synchronized void collectPortion()
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

    public synchronized void watchTheNews()
    {
        Chef c = (Chef) Thread.currentThread();
        CommunicationChannel com = new CommunicationChannel(serverHostName, serverPortNumb);
        Object[] params = new Object[0];
    	Object[] state_fields = new Object[2];
        state_fields[0] = c.getChefID();
    	state_fields[1] = c.getChefState();

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

        c.setState((clientSide.entities.ChefState) m_fromServer.getStateFields()[1]);
        
        com.close ();
    }

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
