package main;
import entities.*;
import sharedRegions.bar.*;
import sharedRegions.kitchen.Kitchen;
import sharedRegions.table.*;
import genclass.GenericIO;

public class MainProgram 
{
    public static void main(String[] args)
    {
        Bar bar;
        Kitchen kitchen;
        Table Table;
        Chef chef = new Chef(ChefState.WAITING_FOR_AN_ORDER);
        Waiter waiter = new Waiter(WaiterState.APPRAISING_SITUATION);
        Student[] students = new Student[7];

        for(int i=0;i<students.length;i++)
        {
            students[i] = new Student(i,StudentState.GOING_TO_THE_RESTAURANT);
        }
        GenericIO.writelnString(chef.getState().toString());
        GenericIO.writelnString(waiter.getState().toString());
        GenericIO.writelnString(students[1].getState().toString());
    }
}
