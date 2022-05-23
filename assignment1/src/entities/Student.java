package entities;

import sharedRegions.Bar;
import sharedRegions.Table;
import main.Constants;

    /**
    *       Student Thread.
    *
    *       Class used to simulate a student's life cycle   
    *    
    */

public class Student extends Thread
{
    /**
    *       Student State
    */
    private int state;
    /**
    *       Flag to determine if the student has been saluted by the waiter
    */
    private boolean salutedByWaiter = false;
    /**
    *       Flag to determine if the student has read the menu
    */
    private boolean readTheMenu = false;
    /**
    *       Flag to determine if the student has been served by the waiter 
    */
    private boolean servedByWaiter = false;
    /**
    *       Flag to determine if this student was the last to eat 
    */
    private boolean lastStudent = false;
    /**
    *       Student ID
    */
    private int sID;
    /**
    *       Student seat at the table
    */
    private int tableSeat;
    /**
    *       Reference to the shared region "Bar"
    */
    private Bar bar;
    /**
    *       Reference to the shared region "Table"
    */
    private Table table;

    /**
    *   Instantiation of a Student thread.
    *
    *     @param id thread number
    *     @param state student state
    *     @param bar reference to the bar 
    *     @param table reference to the table
    */
    public Student(int id, int state, Bar bar, Table table)
    {
        this.sID=id;
        this.state = state;
        this.bar = bar;
        this.table = table;
    }

    public boolean isLastStudent()
    {
        return lastStudent;
    }

    public void setLastStudent(boolean lastStudent) 
    {
        this.lastStudent = lastStudent;
    }

    /**
    *   Get readTheMenu boolean      
    */
    public boolean getReadTheMenu() {
        return readTheMenu;
    }

    /**
    *   Set readTheMenu boolean      
    */
    public void setReadTheMenu() {
        this.readTheMenu = true;
    }

    /**
    *   Get table seat number 
    */
    public int getTableSeat() {
        return tableSeat;
    }

    /**
    *   Set table seat number 
    */
    public void setTableSeat(int tableSeat) {
        this.tableSeat = tableSeat;
    }

    /**
    *   Get served by waiter boolean
    */
    public boolean servedByWaiter() 
    {
        return servedByWaiter;
    }

    /**
    *   Get served by waiter boolean
    */
    public void setServedByWaiter(boolean served) 
    {
        this.servedByWaiter = served;
    }

    /**
    *   Get saluted by waiter boolean
    */
    public boolean getSalutedByWaiter() 
    {
        return salutedByWaiter;
    }

    /**
    *   Set saluted by waiter boolean
    */
    public void setSalutedByWaiter() 
    {
        this.salutedByWaiter = true;
    }

    /**
    *   Set Student state 
    */
    public void setState(int state)
    {
        this.state = state;
    }

    /**
    *   Get Student state 
    */
    public int getStudentState()
    {
        return this.state; 
    }

    /**
    *   Get Student ID 
    */
    public int getID()
    {
        return this.sID;
    }

    /**
    *   Life Cycle of the Student
    */
    @Override
    public void run()
    {
        walkABit();
        bar.enter();
        bar.readTheMenu();

        if(bar.FirstStudent(this.sID))
        {
            table.prepareTheOrder();
            while(!table.hasEverybodyChosen())
            {
                table.addUpOnesChoice();
            }
            // Means the first student has registered everybody choice
            bar.callTheWaiter();
            table.describeTheOrder();
            table.joinTheTalk();
        }
        else
        {
            table.informCompanion();
        }

        int courses = 0;
        while(courses < Constants.courses_number)//while(!table.isOrderDone());
        {
            // wait while it's not served
            table.waitingToBeServed(this.sID);
            table.startEating();
            table.endEating();
            //System.out.printf("Student[%d] finshed eating\n", this.sID);

            table.hasEverbodyFinished();
            bar.signalTheWaiter(this.sID);
            courses++;
        }

        if(bar.shouldHaveArrivedEarlier(this.sID))
        {
            table.honourTheBill();
            System.out.printf("Student[%d] payed the bill !!!\n", this.sID);
        }

        bar.exit();
        System.out.printf("\033[41m Student[ " + this.sID + "] End Of Life \033[0m\n");
    }    
    
    /**
    *   Simulate a walk to the bar of a student with a random time interval 
    */
    public void walkABit() 
    {
        try {
            sleep((long) (3 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
    *   Simulate the student eating with a random time interval 
    */
    public void studentEating() 
    {
        try {
            sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
