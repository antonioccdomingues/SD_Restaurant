package serverSide.main;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.sharedRegions.Table;
import serverSide.sharedRegions.TableMessageExchange;
import serverSide.stubs.GeneralReposStub;
import serverSide.sharedRegions.SharedRegionInterface;
import serverSide.entities.ServiceProviderAgent;

/**
 *    Server side of the Departure Airport Shared Region.
 *
 *    Request serialization variant.
 *    It waits for the start of the message exchange.
 */

public class TableMain
{
  /**
   *    Main method.
   *
   *    @param args runtime arguments
   */

    public static void main (String[] args)
    {
      /* service is established */

       ServerCom serverCom, sconi;                                        // communication channels
       int portNumb = 22343;                                          // port nunber for listening to service requests

       serverCom = new ServerCom (portNumb);
       serverCom.start ();                             // service is instantiated
       serverCom.setSoTimeout(10000);
       GenericIO.writelnString ("Service is established!");
       GenericIO.writelnString ("Server is listening for service requests.");

       GeneralReposStub generalReposStub = new GeneralReposStub("l040101-ws10.ua.pt", 22345);
       Table table = new Table(generalReposStub);
       SharedRegionInterface sharedRegionInterface = new TableMessageExchange(table);
       
      /* service request processing */
                                        // service provider agent
       while (!sharedRegionInterface.hasShutdown())
       { 
    	 try {
	    	 sconi = serverCom.accept ();                                     // enter listening procedure
	         ServiceProviderAgent serviceProviderAgent = new ServiceProviderAgent (sconi, sharedRegionInterface);            // start a service provider agent to address
	         serviceProviderAgent.start ();      
    	 } 
    	 catch(SocketTimeoutException ste) {}
       }
       
       GenericIO.writelnString ("Service is closed!");
    }
}