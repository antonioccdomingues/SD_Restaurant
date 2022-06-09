package serverSide.objects;

import commInfra.*;
import clientSide.entities.WaiterState;
import clientSide.entities.StudentState;
import clientSide.entities.Waiter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import genclass.GenericIO;

import clientSide.entities.ChefState;
import clientSide.entities.Student;
import interfaces.GeneralReposInterface;
import serverSide.main.Constants;
import serverSide.main.BarMain;
import interfaces.*;

/**
 *    Bar.
 *
 *    It is responsible for the the synchronization of the Waiter, Chef and student
 *    and is implemented as an implicit monitor.
 *    
 */

public class Bar implements BarInterface 
{
    private int firstStudentID;
    private int lastStudentID=-1;
    private int studentsEntered;
    private int studentsDone;
    private int waiterIsRequested = 0;
    private int studentAtDoor = 0;
    private int portionReady = 0;
    private int courses = 0;
    private boolean firstStudent = true;
    private boolean orderDone = false;
    private boolean payBill = false;
    private boolean allStudentsLeft = false;
    private final boolean[] students_saluted;
    private final boolean[] students_menu;
    private MemFIFO<Integer> queue;
    private final GeneralReposInterface repos;   //references to general repository

    /**
	*  Bar instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Bar(GeneralReposInterface repos)
    {
        students_saluted = new boolean[Constants.students_number];
        students_menu = new boolean[Constants.students_number];
        for(int i=0; i<Constants.students_number;i++)
        {
            students_saluted[i] = false;
            students_menu[i] = false;
        }

        try 
        {
            queue = new MemFIFO<>(new Integer[Constants.students_number]);
        } catch (MemException e) 
        {
            e.printStackTrace();
        }
        this.repos = repos;
    }

    /**
     *  
     *  It is called to salute all students
     *
     */

    public synchronized ReturnValue saluteTheClient() throws RemoteException
    {
        //Dequeue the student from the fifo and salute him
        // so he can transition to the next state
        int student=0;

        // Dequeue the student
        try {
            student = queue.read();
        } catch (MemException e) {
            e.printStackTrace();
        }

        repos.setWaiterState(WaiterState.PRESENTING_THE_MENU);

        students_saluted[student] = true;
        // wake student
        notifyAll();

        // set the seat of the student upon being saluted
        //students[student_saluted].setTableSeat(this.studentsSeat);
        //this.studentsSeat++;

        // After being in presenting the menu state
        // we wait here, before returning to the bar
        // untill the student has read the menu
        while(!students_menu[student] == false)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return new ReturnValue(false, WaiterState.PRESENTING_THE_MENU, 0);
    }

    /**
     *  
     *  It is called by the Chef to wake the thread
     *
     */
    public synchronized ReturnValue alertTheWaiter() throws RemoteException
    {
        repos.setChefState(ChefState.DELIVERING_THE_PORTIONS);
        this.portionReady++;
        this.waiterIsRequested++; 
        notifyAll();
        return new ReturnValue(false, ChefState.DELIVERING_THE_PORTIONS, 0);
    }

    
    public synchronized ReturnValue returningToTheBar() throws RemoteException
    {
        repos.setWaiterState(WaiterState.APPRAISING_SITUATION);
        return new ReturnValue(false, WaiterState.APPRAISING_SITUATION, 0);
    }

    public synchronized ReturnValue prepareTheBill() throws RemoteException
    {
        repos.setWaiterState(WaiterState.PROCESSING_THE_BILL);
        return new ReturnValue(false, WaiterState.PROCESSING_THE_BILL, 0);
    }

    public synchronized ReturnValue lookAround() throws RemoteException
    {
        repos.setWaiterState(WaiterState.APPRAISING_SITUATION);

        // while the waiter is not called to be 
        // awakened by other events, he simply waits here

        while(this.waiterIsRequested == 0)
        {
            try {
                wait();
            } catch (InterruptedException e) 
            {
            }
        }
        this.waiterIsRequested--; 
        
        // After the wait block, means he is requested
        // Depending on the flag that is activated, he will 
        // do some action

        //means there's students at the door, waiting to be saluted
        if(this.studentAtDoor > 0)
        {
            this.studentAtDoor--; 
            return new ReturnValue(false, WaiterState.APPRAISING_SITUATION, 0);
        }
        else if(this.orderDone)
        {
            this.orderDone = false;
            return new ReturnValue(false, WaiterState.APPRAISING_SITUATION, 1);
        }
        else if(this.portionReady > 0)// && this.waitingForCourse)
        {
            this.portionReady--; 
            return new ReturnValue(false, WaiterState.APPRAISING_SITUATION, 2);
        }
        else if(this.payBill == true)
        {
            this.payBill = false;
            return new ReturnValue(false, WaiterState.APPRAISING_SITUATION, 3);
        }
        else if(this.allStudentsLeft)
        {
            //((Waiter) Thread.currentThread()).setCanGoHome();
            return new ReturnValue(false, WaiterState.APPRAISING_SITUATION, 4);
        }
        else
        {
            return new ReturnValue(false, WaiterState.APPRAISING_SITUATION, -1);
        }
    }

    public synchronized ReturnValue sayGoodbye() throws RemoteException
    {
        repos.setWaiterState(WaiterState.APPRAISING_SITUATION);
        // While we haven't said goodbye to all students
        // If we've said goodbye to everyone, the waiter can go home
        //((Waiter) Thread.currentThread()).setCanGoHome();
        return new ReturnValue(false, WaiterState.APPRAISING_SITUATION, 0);
    }

    public synchronized ReturnValue signalTheWaiter(int sID, boolean last) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.CHATTING_WITH_COMPANIONS);
        // last student notified the waiter, and thus
        // wont be waiting in the cycle
        if(last)
        {
            //((Student) Thread.currentThread()).setLastStudent(false);
            System.out.printf("Student[%d] was last to eat, will alert the waiter\n", sID);
            //notify the waiter
            this.courses ++;
            this.waiterIsRequested++; 
            notifyAll();
            return new ReturnValue(true, StudentState.CHATTING_WITH_COMPANIONS, 0);
        }

        return new ReturnValue(false, StudentState.CHATTING_WITH_COMPANIONS, 0);
    }

    public synchronized ReturnValue callTheWaiter(int sID) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.ORGANIZING_THE_ORDER); 
        this.orderDone = true;
        // wake up waiter
        this.waiterIsRequested++;
        notifyAll();
        return new ReturnValue(false, StudentState.ORGANIZING_THE_ORDER, 0);
    }

    public synchronized ReturnValue enter(int sID) throws RemoteException
    {

        try {
            queue.write(sID);
            this.studentsEntered++;
        } catch (MemException e1) {
            e1.printStackTrace();
        }

        // Wake waiter
        this.studentAtDoor++; 
        this.waiterIsRequested++; 
        notifyAll();

        if(firstStudent)
        {
            this.firstStudent = false;
            this.firstStudentID = sID;
            System.out.printf("First Student %d \n", this.firstStudentID);
        }
        // means this is the last student thread
        if(this.studentsEntered==Constants.students_number)
        {
            this.lastStudentID = sID;
            System.out.printf("Last Student %d \n", this.lastStudentID);
        }


        // block while it is not saluted
        while(!students_saluted[sID] == false)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Update the state
        repos.setStudentState(sID, StudentState.TAKING_A_SEAT_AT_THE_TABLE); 
        return new ReturnValue(false, StudentState.TAKING_A_SEAT_AT_THE_TABLE, 0);
    }

    public synchronized ReturnValue FirstStudent(int sID) throws RemoteException
    {
        if(this.firstStudentID == sID)
        {
            return new ReturnValue(true, 0, 0);
        }
        else
            return new ReturnValue(false, 0, 0);
    }

    public synchronized ReturnValue exit(int sID) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.GOING_HOME);
        this.studentsDone++;

        if(this.studentsDone==Constants.students_number)
        {
            this.allStudentsLeft = true;
            this.waiterIsRequested++; 
            notifyAll();
        }
        return new ReturnValue(false, StudentState.GOING_HOME, 0);
    }

    public synchronized ReturnValue shouldHaveArrivedEarlier(int sID) throws RemoteException
    {
        if(this.lastStudentID == sID)
        {
            repos.setStudentState(sID, StudentState.PAYING_THE_BILL);
            this.waiterIsRequested++;
            this.payBill = true;
            notifyAll();
            return new ReturnValue(true, StudentState.PAYING_THE_BILL, 0);
        }
        else
            return new ReturnValue(false, StudentState.PAYING_THE_BILL, 0);
    }

    public synchronized ReturnValue readTheMenu(int sID) throws RemoteException
    {
        // Means he transitioned from previous state, having selected the course
        students_menu[sID] = true;
        notifyAll();

        repos.setStudentState(sID, StudentState.SELECTING_THE_COURSES);
        return new ReturnValue(false, StudentState.SELECTING_THE_COURSES, 0);
    }

    public synchronized void shutdown () throws RemoteException
    {
        //nEntities += 1;
        //if (nEntities >= ExecConst.E_DepAir) {
        	
        	try
        	{ repos.shutdown();
        	}
        	catch (RemoteException e)
        	{ GenericIO.writelnString ("Customer generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
	          System.exit (1);
        	}
        	BarMain.shutdown ();
        //}
        notifyAll ();                                       // the barber may now terminate
    }
}