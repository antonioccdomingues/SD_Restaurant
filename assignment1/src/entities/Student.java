package entities;

import sharedRegions.Bar;
import sharedRegions.Table;
import main.Constants;

public class Student extends Thread
{
    private StudentState state;
    private boolean salutedByWaiter = false;
    private boolean readTheMenu = false;
    private boolean servedByWaiter = false;
    private int sID;
    private int tableSeat;
    private Bar bar;
    private Table table;
    public boolean lastStudent = false;

    public Student(int id, StudentState state, Bar bar, Table table)
    {
        this.sID=id;
        this.state = state;
        this.bar = bar;
        this.table = table;
    }

    public boolean getReadTheMenu() {
        return readTheMenu;
    }

    public void setReadTheMenu() {
        this.readTheMenu = true;
    }

    public int getTableSeat() {
        return tableSeat;
    }

    public void setTableSeat(int tableSeat) {
        this.tableSeat = tableSeat;
    }

    public boolean servedByWaiter() 
    {
        return servedByWaiter;
    }

    public void setServedByWaiter(boolean served) 
    {
        this.servedByWaiter = served;
    }

    public boolean getSalutedByWaiter() 
    {
        return salutedByWaiter;
    }

    public void setSalutedByWaiter() 
    {
        this.salutedByWaiter = true;
    }

    public void setState(StudentState state)
    {
        this.state = state;
    }

    public StudentState getStudentState()
    {
        return this.state; 
    }

    public int getID()
    {
        return this.sID;
    }
    
    @Override
    public String toString() 
    {
        return this.state.toString();
    }

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
            System.out.printf("Student[%d] finshed eating\n", this.sID);

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
    
    public void walkABit() 
    {
        try {
            sleep((long) (2 + 1000 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void studentEating() 
    {
        try {
            sleep((long) (5 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
