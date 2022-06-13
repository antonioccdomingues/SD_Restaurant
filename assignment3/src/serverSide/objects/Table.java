package serverSide.objects;

import commInfra.*;
import clientSide.entities.WaiterState;
import clientSide.entities.StudentState;

import java.rmi.RemoteException;

import genclass.GenericIO;

import interfaces.GeneralReposInterface;
import serverSide.main.Constants;
import serverSide.main.TableMain;
import interfaces.*;

/**
 *    Table.
 *
 *    It is responsible for the the synchronization of the Waiter and Student
 *    and is implemented as an implicit monitor.
 *    
 */
public class Table implements TableInterface
{
    private boolean orderDescribed = false;
    public boolean everyBodyFinished = false;
    private boolean firstStudentJoinedTalk = false;
    private boolean billIsReady = false;
    private boolean billIsPaid = false;
    private int studentSelectedCourses;
    private int studentServed = 0;
    private int studentFinishedEating;
    private int coursesDelivered = 0;
    private int studentWaiting = 0;
    private int nEntities=0;
    private double delay = 0;
    private final boolean[] students;
    private MemFIFO<Integer> queue;
    private final GeneralReposInterface repos;   //references to general repository    

    /**
	*  Table instantiation.
	*
	*    @param repos reference to the general repository
	*/
    public Table(GeneralReposInterface repos)
    {
        students = new boolean[Constants.students_number];
        for(int i=0; i<Constants.students_number;i++)
        {
            students[i] = false;
        }

        try 
        {
            queue = new MemFIFO<>(new Integer[Constants.students_number]);
        } catch (MemException e) 
        {
            e.printStackTrace();
        }
        this.repos = repos;
        this.delay = 300 * Math.random();
    } 

    public synchronized ReturnValue getThePad() throws RemoteException
    {
        repos.setWaiterState(WaiterState.TAKING_THE_ORDER);
        System.out.println("WAiter waiting");
        while(!this.orderDescribed)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new ReturnValue(false, WaiterState.TAKING_THE_ORDER, 0);
    }

    public synchronized void deliverPortion() throws RemoteException
    {
        int student_served=0;

        while(this.studentWaiting == 0)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.studentWaiting--;
        // Dequeue the student
        try {
            student_served = queue.read();
        } catch (MemException e) 
        {
            System.out.println("No One to be served");
            System.out.println("But counted one as delivered");
        }

        students[student_served] = true;
        notifyAll();
    }

    public synchronized ReturnValue haveAllClientsBeenServed() throws RemoteException
    {
        repos.setWaiterState(WaiterState.WAITING_FOR_PORTION);
        if(this.studentServed==Constants.students_number)
        {
            this.studentServed=0;
            return new ReturnValue(true, WaiterState.WAITING_FOR_PORTION, 0);
        }
        else
            return new ReturnValue(false, WaiterState.WAITING_FOR_PORTION, 0);
    }

    public synchronized ReturnValue presentTheBill() throws RemoteException
    {
        this.billIsReady = true;
        // wake up the student waiting for the biill
        notifyAll();
        repos.setWaiterState(WaiterState.RECEIVING_PAYMENT);

        // wait while the bill is not paid
        while(!this.billIsPaid)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new ReturnValue(false, WaiterState.RECEIVING_PAYMENT, 0);
    }

    public synchronized ReturnValue informCompanion(int sID) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.CHATTING_WITH_COMPANIONS);
        
        this.studentSelectedCourses++;
        // Wake the student taking the order
        notifyAll();
        
        // Wait untill the course is served 
        while(!this.firstStudentJoinedTalk)
        {
            try 
            {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new ReturnValue(false, StudentState.CHATTING_WITH_COMPANIONS, 0);
    }

    public synchronized ReturnValue prepareTheOrder(int sID) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.ORGANIZING_THE_ORDER);
        this.studentSelectedCourses++;
        return new ReturnValue(false, StudentState.ORGANIZING_THE_ORDER, 0);
    }
    
    public synchronized ReturnValue joinTheTalk(int sID) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.CHATTING_WITH_COMPANIONS);

        this.firstStudentJoinedTalk = true;
        notifyAll();
        return new ReturnValue(false, StudentState.CHATTING_WITH_COMPANIONS, 0);
    }

    public synchronized ReturnValue hasEverbodyFinished() throws RemoteException
    {
        while(!this.everyBodyFinished) 
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ReturnValue(false, StudentState.CHATTING_WITH_COMPANIONS, 0);
    }

    public synchronized ReturnValue startEating(int sID) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.ENJOYING_THE_MEAL);
        // Simulate eating
        //((Student) Thread.currentThread()).studentEating();
        return new ReturnValue(false, StudentState.ENJOYING_THE_MEAL, 0);
    }

    public synchronized ReturnValue endEating(int sID) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.CHATTING_WITH_COMPANIONS);
        this.studentFinishedEating++;
        //repos.setNPortion(1);
        // served is false
        students[sID] = false;

        if(this.studentFinishedEating==Constants.students_number)
        {
            // means he was the last one
            // ((Student) Thread.currentThread()).setLastStudent(true);
            System.out.printf("Student[%d] was last to eat\n", sID);
            this.everyBodyFinished = true;
            this.studentFinishedEating=0;
            notifyAll();
            return new ReturnValue(true, StudentState.CHATTING_WITH_COMPANIONS, 0);
        }
        
        return new ReturnValue(false, StudentState.CHATTING_WITH_COMPANIONS, 0);
    }

    public synchronized void honourTheBill() throws RemoteException
    {
        // wait untill the bill is ready
        while(!this.billIsReady)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // after the waiting cycle, we pay it
        this.billIsPaid = true;
        notifyAll();
    }

    public synchronized ReturnValue addUpOnesChoice(int sID) throws RemoteException
    {
        repos.setStudentState(sID, StudentState.ORGANIZING_THE_ORDER);

        while(this.studentSelectedCourses < Constants.students_number)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new ReturnValue(false, StudentState.ORGANIZING_THE_ORDER, 0);
    }

    public synchronized ReturnValue hasEverybodyChosen() throws RemoteException
    {
        if(this.studentSelectedCourses==Constants.students_number)
        {
            return new ReturnValue(true, 0, 0);
        }
        else
        {
            return new ReturnValue(false, 0 , 0);
        }
    }

    public synchronized ReturnValue describeTheOrder(int sID) throws RemoteException
    {
        System.out.println("Student descried order");
        repos.setStudentState(sID, StudentState.ORGANIZING_THE_ORDER); 
        this.orderDescribed = true;
        notifyAll();
        return new ReturnValue(false, StudentState.ORGANIZING_THE_ORDER , 0);
    }

    public synchronized void waitingToBeServed(int sID) throws RemoteException
    {
        // Add the student to the waiting to be served queue 
        try {
            queue.write(sID);
        } catch (MemException e1) {
            e1.printStackTrace();
        }
        this.studentWaiting++;
        // wake waiter waiting to serve 
        notifyAll();
        // block while it is not served
        while(students[sID] == false)
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.everyBodyFinished = false;
        this.studentServed++;
        repos.setNPortion(1);
    }

    public synchronized void shutdown () throws RemoteException
    {
        nEntities += 1;
        if (nEntities >= Constants.E_Table) {
        	
        	try
        	{ repos.shutdown();
        	}
        	catch (RemoteException e)
        	{ GenericIO.writelnString ("Customer generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
	          System.exit (1);
        	}
        	TableMain.shutdown ();
        }
        notifyAll ();                                       // the barber may now terminate
    }
}