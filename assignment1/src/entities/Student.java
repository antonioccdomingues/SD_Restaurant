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

    }
}
