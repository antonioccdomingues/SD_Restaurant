package clientSide.main;

import clientSide.entities.Student;
import clientSide.entities.StudentState;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import genclass.GenericIO;
import interfaces.TableInterface;
import interfaces.BarInterface;


/**
 *    Client side of the airlift project
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on Java RMI.
 */

public class StudentMain {
	
	/**
	   *  Main method.
	   *
	   *    @param args runtime arguments
	   *        args[0] - name of the platform where is located the RMI registering service
	   *        args[1] - port number where the registering service is listening to service requests
	   */
	
	public static void main(String args[])
    {
	    /* get location of the generic registry service */

	     String rmiRegHostName;
	     int rmiRegPortNumb = -1;

	     /* getting problem runtime parameters */

	     if (args.length != 2)
	        { GenericIO.writelnString ("Wrong number of parameters!");
	          System.exit (1);
	        }
	     rmiRegHostName = args[0];
	     try
	     { rmiRegPortNumb = Integer.parseInt (args[1]);
	     }
	     catch (NumberFormatException e)
	     { GenericIO.writelnString ("args[1] is not a number!");
	       System.exit (1);
	     }
	     if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536))
	        { GenericIO.writelnString ("args[1] is not a valid port number!");
	          System.exit (1);
	        }

	    /* look for the remote object by name in the remote host registry */

	     String nameEntryTable = "Table";  
	     String nameEntryBar = "Bar";
	     
	     TableInterface Table = null;
	     BarInterface Bar = null;
	     
	     Registry registry = null;
	     
	     Student [] students = new Student [Constants.students_number];

	     try
	     { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
	     }
	     catch (RemoteException e)
	     { GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
	       e.printStackTrace ();
	       System.exit (1);
	     }
	     
	     //Lookup all the stubs
	    
	     try
	     { Table = (TableInterface) registry.lookup (nameEntryTable);
	     }
	     catch (RemoteException e)
	     { 
	       e.printStackTrace ();
	       System.exit (1);
	     }
	     catch (NotBoundException e)
	     { 
	       e.printStackTrace ();
	       System.exit (1);
	     }
	     
	     try
	     { Bar = (BarInterface) registry.lookup (nameEntryBar);
	     }
	     catch (RemoteException e)
	     { 
	       e.printStackTrace ();
	       System.exit (1);
	     }
	     catch (NotBoundException e)
	     { 
	       e.printStackTrace ();
	       System.exit (1);
	     }
	    
         for (int i = 0; i < Constants.students_number; i++)
         students[i] = new Student (i, StudentState.GOING_TO_THE_RESTAURANT, Bar, Table);
	     
         /* start of the simulation */
         
         for (int i = 0; i < Constants.students_number; i++)
         students[i].start ();

	    /* wait for the end */
        for (int i = 0; i < Constants.students_number; i++)
        { try
        { students[i].join ();
        }
        catch (InterruptedException e) {}
        System.out.println("The Student "+(i+1)+" just terminated");
        }
        
        try
        { Bar.shutdown ();
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Customer generator remote exception on Restaurant shutdown: " + e.getMessage ());
          System.exit (1);
        }
       try
        { Table.shutdown ();
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Customer generator remote exception on Restaurant shutdown: " + e.getMessage ());
          System.exit (1);
        }
        
        System.out.println("End of the Simulation");
	      
	}
}
