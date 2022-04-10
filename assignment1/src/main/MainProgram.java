package main;

import entities.*;
import sharedRegions.*;
import genclass.GenericIO;

public class MainProgram 
{
    public static void main(String[] args)
    {
        Bar bar = new Bar();
        Kitchen kitchen = new Kitchen();
        Table table = new Table();

        Chef chef = new Chef(ChefState.WAITING_FOR_AN_ORDER, kitchen, bar);
        chef.start();

        Waiter waiter = new Waiter(WaiterState.APPRAISING_SITUATION, kitchen, bar, table);
        waiter.start();

        Student[] students = new Student[7];
        for(int i=0;i<students.length;i++)
        {
            students[i] = new Student(i,StudentState.GOING_TO_THE_RESTAURANT, bar, table);
            students[i].start(); 
        }

        GenericIO.writelnString(students[3].getStudentState().toString());
    }
}
