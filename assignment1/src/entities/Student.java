package entities;

import sharedRegions.Bar;
import sharedRegions.Table;
import genclass.*;

public class Student extends Thread
{
    private StudentState state;
    private boolean salutedByWaiter = false;
    private int sID;
    private Bar bar;
    private Table table;

    public Student(int id, StudentState state, Bar bar, Table table)
    {
        this.sID=id;
        this.state = state;
        this.bar = bar;
        this.table = table;
    }

    public boolean getSalutedByWaiter() 
    {
        return salutedByWaiter;
    }

    public void setSalutedByWaiter(boolean salutedByWaiter) 
    {
        this.salutedByWaiter = salutedByWaiter;
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
        try {
            table.walkABit();
        } catch (InterruptedException e) {
            System.exit(1);
        }
        table.enter();
        table.readTheMenu();

        if(table.FirstStudent(this.sID))
        {
            while(!table.hasEverybodyChosen())
            {
                table.addUpOnesChoice();
            }
            bar.callTheWaiter();
            table.describeTheOrder();
            table.joinTheTalk();
        }
        else
        {
            table.informCompanion();
        }

        table.startEating();
        table.endEating();

        while(!table.hasEverbodyFinished()); // blocking
        bar.signalTheWaiter();

        if(table.shouldHaveArrivedEarlier(this.sID))
        {
            table.honourTheBill();
        }
        table.exit();
        String s = "\033[41m Student[ " + this.sID + "] End Of Life \033[0m";
        GenericIO.writelnString(s);
    }
}
