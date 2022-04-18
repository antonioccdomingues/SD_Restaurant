package main;

import entities.*;
import sharedRegions.*;
import genclass.FileOp;
import genclass.GenericIO;

public class MainProgram 
{
    public static int students_number = 7;
    public static int courses_number = 3;
    public static void main(String[] args)
    {
        
        GeneralRepos repos;
        String fileName;                    // logging file name
		char opt;                           // selected option
		boolean success;                    // end of operation flag
        

        GenericIO.writelnString ("\n" + "Problem of the Restaurant\n");
		do{
			GenericIO.writeString ("Logging file name? ");
			fileName = GenericIO.readlnString ();
			if (FileOp.exists (".", fileName)){ 
				do{
					GenericIO.writeString ("There is already a file with this name. Delete it (y - yes; n - no)? ");
					opt = GenericIO.readlnChar ();
				} while ((opt != 'y') && (opt != 'n'));
				if (opt == 'y')
					success = true;
				else success = false;
			}
			else success = true;
		} while (!success);

        repos = new GeneralRepos (fileName);

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


        try {
            chef.join();
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
        try {
            waiter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
