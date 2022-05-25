package serverSide.main;

import java.net.SocketTimeoutException;

import commInfra.ServerCom;
import genclass.GenericIO;
import serverSide.sharedRegions.Bar;
import serverSide.sharedRegions.BarMessageExchange;
import serverSide.sharedRegions.SharedRegionInterface;
import serverSide.stubs.GeneralReposStub;
import serverSide.entities.ServiceProviderAgent;

/**
 *    Server side of the Departure Airport Shared Region.
 *
 *    Request serialization variant.
 *    It waits for the start of the message exchange.
 */

public class BarMain
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
       int portNumb = 22341;                                          // port nunber for listening to service requests

       serverCom = new ServerCom (portNumb);
       serverCom.start ();                             // service is instantiated
       serverCom.setSoTimeout(10000);
       GenericIO.writelnString ("Service is established!");
       GenericIO.writelnString ("Server is listening for service requests.");

       GeneralReposStub generalReposStub = new GeneralReposStub("l040101-ws10.ua.pt", 22345);
       Bar bar = new Bar(generalReposStub);
       SharedRegionInterface sharedRegionInterface = new BarMessageExchange(bar);
       
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


       // The bar is the last region to be shutdown
      generalReposStub.shutdown();
      GenericIO.writelnString("\033[41m Service is closed! \033[0m");
    }
}