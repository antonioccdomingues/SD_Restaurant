package main;

import entities.*;
import sharedRegions.*;

public class MainProgram 
{
    public static int students_number = 7;
    public static int courses_number = 3;
    public static void main(String[] args)
    {
        Bar bar = new Bar();
        Kitchen kitchen = new Kitchen();
        Table table = new Table();

        Chef chef = new Chef(ChefState.WAITING_FOR_AN_ORDER, kitchen, bar);
        Waiter waiter = new Waiter(WaiterState.APPRAISING_SITUATION, kitchen, bar, table);
        Student[] students = new Student[students_number];
        for(int i=0;i<students.length;i++)
            students[i] = new Student(i,StudentState.GOING_TO_THE_RESTAURANT, bar, table);
        
        chef.start();
        waiter.start();
        for(int i=0;i<students.length;i++)
            students[i].start();


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
        for(int i=0;i<students.length;i++)
        {
            try {
                students[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }

    }
}
