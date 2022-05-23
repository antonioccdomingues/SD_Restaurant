package main;

import entities.*;
import sharedRegions.*;
import genclass.GenericIO;

public class MainProgram 
{
    public static void main(String[] args)
    {
        
        GeneralRepos repos;
        GenericIO.writelnString ("\n" + "Problem of the Restaurant\n");
        repos = new GeneralRepos ("log.txt");

        Bar bar = new Bar(repos);
        Kitchen kitchen = new Kitchen(repos);
        Table table = new Table(repos);

        Chef chef = new Chef(0, kitchen, bar);
        Waiter waiter = new Waiter(0, kitchen, bar, table);
        Student[] students = new Student[Constants.students_number];
        for(int i=0;i<students.length;i++)
            students[i] = new Student(i,0, bar, table);
        
        chef.start();
        chef.setName("Chef");
        waiter.start();
        waiter.setName("Waiter");
        for(int i=0;i<students.length;i++)
        {
            students[i].start();
            students[i].setName("Student["+i+"]");
        }


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
