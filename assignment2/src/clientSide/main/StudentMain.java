package clientSide.main;

import clientSide.entities.Student;
import clientSide.entities.StudentState;
import clientSide.stubs.BarStub;
import clientSide.stubs.TableStub;

/**
 *    Client side of the Assignment 2 - Student.
 *    Static solution Attempt (number of threads controlled by global constants - ExecConst)
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class StudentMain 
{
     /**
     *    Main method.
     *
     *    @param args runtime arguments
     */
    
    public static void main(String[] args)
    {
        Student[] students = new Student[Constants.students_number];

        TableStub table;
        BarStub bar;

        table = new TableStub("l040101-ws03.ua.pt", 22343);
        bar = new BarStub("l040101-ws01.ua.pt", 22341);
        
        for(int i = 0; i< Constants.students_number; i++)
        {
            students[i] = new Student(i, StudentState.GOING_TO_THE_RESTAURANT, bar, table);
        }

        // Start
        for(int i = 0; i< Constants.students_number; i++)
        {
            students[i].start(); 
        }

        // End
        for(int i = 0; i< Constants.students_number; i++)
        {
            try {
                students[i].join(); 
            } catch (Exception e) {
            }

            System.out.println("The Student[" + i + "] thread has been terminated");
        }
    }
}
