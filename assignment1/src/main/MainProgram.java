package main;

import entities.*;
import sharedRegions.*;
import genclass.GenericIO;

public class MainProgram 
{
    public static final int students_number = 7;
    public static final int courses_number = 3;
    public static void main(String[] args)
    {
        
        GeneralRepos repos;
        GenericIO.writelnString ("\n" + "Problem of the Restaurant\n");
        repos = new GeneralRepos ("log.txt");

        Bar bar = new Bar(repos);
        Kitchen kitchen = new Kitchen(repos);
        Table table = new Table(repos);

        Chef chef = new Chef(ChefState.WAITING_FOR_AN_ORDER, kitchen, bar);
        Waiter waiter = new Waiter(WaiterState.APPRAISING_SITUATION, kitchen, bar, table);
        Student[] students = new Student[students_number];
        for(int i=0;i<students.length;i++)
            students[i] = new Student(i,StudentState.GOING_TO_THE_RESTAURANT, bar, table);
        
        chef.start();
        waiter.start();
        for(int i=0;i<students.length;i++)
            students[i].start();


        for(int i=0;i<students.length;i++)
        {
            try {
                students[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
        try {
            chef.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            waiter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
