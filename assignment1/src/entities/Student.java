package entities;

import sharedRegions.Bar;
import sharedRegions.Table;

public class Student extends Thread
{
    private StudentState state;
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
        int sID = 0;
        table.walkABit();
        table.enter();
        table.readTheMenu();

        if(table.FirstStudent(sID))
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

        if(table.shouldHaveArrivedEarlier(sID))
        {
            table.honourTheBill();
        }

        table.exit();
    }
}
