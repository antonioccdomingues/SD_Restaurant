package entities;

public class Student extends Thread
{
    private StudentState state;
    private int sID;

    public Student(int id, StudentState state)
    {
        this.sID=id;
        this.state = state;
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
