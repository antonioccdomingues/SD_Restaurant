package clientSide.main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import clientSide.entities.Chef;
import clientSide.entities.ChefState;

import genclass.GenericIO;

import interfaces.KitchenInterface;
import interfaces.BarInterface;


/**
 *    Client side of the airlift project
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on Java RMI.
 */

public class ChefMain {
	
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

	     String nameEntryKitchen = "Kitchen";  
	     String nameEntryBar = "Bar";
	     
	     KitchenInterface Kitchen = null;
	     BarInterface Bar = null;
	     
	     Registry registry = null;
	     
	     Chef chef;

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
	     { Kitchen = (KitchenInterface) registry.lookup (nameEntryKitchen);
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
	     
	     chef = new Chef(0, ChefState.WAITING_FOR_AN_ORDER, Kitchen, Bar);
	      
         /* start of the simulation */
         chef.start();
	     
	     /* wait for the end */
	     try
	     { chef.join ();
	     }
	     catch (InterruptedException e) {}
	     System.out.println("The Chef "+(1)+" just terminated");
	     
	     try
	      { Bar.shutdown ();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Customer generator remote exception on BarberShop shutdown: " + e.getMessage ());
	        System.exit (1);
	      }
	     try
	      { Kitchen.shutdown ();
	      }
	      catch (RemoteException e)
	      { GenericIO.writelnString ("Customer generator remote exception on BarberShop shutdown: " + e.getMessage ());
	        System.exit (1);
	      }
	     
	     System.out.println("End of the Simulation");   
	  }	
}
