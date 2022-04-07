package entities;

public class Student 
{
    private StudentState state;

    public Student(StudentState state)
    {
        this.state = state;
    }

    public void setState(StudentState state)
    {
        this.state = state;
    }

    public StudentState getState()
    {
        return this.state; 
    }
    
    @Override
    public String toString() 
    {
        return this.state.toString();
    }
}
