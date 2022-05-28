package clientSide.main;

import clientSide.entities.Waiter;
import clientSide.entities.WaiterState;
import clientSide.stubs.KitchenStub;
import clientSide.stubs.TableStub;
import clientSide.stubs.BarStub;

/**
 *    Client side of the Assignment 2 - Waiter.
 *    Static solution Attempt (number of threads controlled by global constants - ExecConst)
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class WaiterMain 
{
    /**
     *    Main method.
     *
     *    @param args runtime arguments
     */

    public static void main(String[] args)
    {
        Waiter waiter;

        KitchenStub kitchen;
        BarStub bar;
        TableStub table;

        kitchen = new KitchenStub("l040101-ws02.ua.pt", 22342);
        bar = new BarStub("l040101-ws01.ua.pt", 22341);
        table = new TableStub("l040101-ws03.ua.pt", 22343);

        waiter = new Waiter(0, WaiterState.APPRAISING_SITUATION,kitchen, bar,table);
        
        /* start thread */
        waiter.start();


        try
        {
            waiter.join();
        }catch(InterruptedException e){}

        System.out.println("The Waiter thread has been terminated");

        
    }
}
