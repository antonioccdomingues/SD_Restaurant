package clientSide.main;

import clientSide.entities.Chef;
import clientSide.entities.ChefState;
import clientSide.stubs.KitchenStub;
import clientSide.stubs.BarStub;

public class ChefMain 
{
    public static void main(String[] args)
    {
        Chef chef;

        KitchenStub kitchen;
        BarStub bar;

        kitchen = new KitchenStub("l040101-ws02.ua.pt", 22340);
        bar = new BarStub("l040101-ws01.ua.pt", 22340);

        chef = new Chef(0, ChefState.WAITING_FOR_AN_ORDER, kitchen, bar);

        chef.start();


        try
        {
            chef.join();
        }catch(InterruptedException e){}

        System.out.println("The Chef thread has been terminated");

        
    }
}
