package clientSide.main;

import clientSide.entities.Waiter;
import clientSide.entities.WaiterState;
import clientSide.stubs.KitchenStub;
import clientSide.stubs.TableStub;
import clientSide.stubs.BarStub;

public class WaiterMain 
{
    public static void main(String[] args)
    {
        Waiter waiter;

        KitchenStub kitchen;
        BarStub bar;
        TableStub table;

        kitchen = new KitchenStub("l040101-ws02.ua.pt", 22342);
        bar = new BarStub("l040101-ws01.ua.pt", 22340);
        table = new TableStub("l040101-ws03.ua.pt", 22341);

        waiter = new Waiter(0, 0,kitchen, bar,table);

        waiter.start();


        try
        {
            waiter.join();
        }catch(InterruptedException e){}

        System.out.println("The Waiter thread has been terminated");

        
    }
}
