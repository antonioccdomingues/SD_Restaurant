package serverSide.entities;


    /**
    *       Student Thread.
    *
    *       Class used to simulate a student's life cycle   
    *    
    */

public interface Student
{
    /**
    *   Get readTheMenu boolean      
    */
    public boolean getReadTheMenu();

    /**
    *   Set readTheMenu boolean      
    */
    public void setReadTheMenu();

    /**
    *   Get table seat number 
    */
    public int getTableSeat();
    /**
    *   Set table seat number 
    */
    public void setTableSeat(int tableSeat);

    /**
    *   Get served by waiter boolean
    */
    public boolean servedByWaiter();

    /**
    *   Get served by waiter boolean
    */
    public void setServedByWaiter(boolean served);

    /**
    *   Get saluted by waiter boolean
    */
    public boolean getSalutedByWaiter();

    /**
    *   Set saluted by waiter boolean
    */
    public void setSalutedByWaiter(); 

    /**
    *   Set Student state 
    */
    public void setState(StudentState state);

    /**
    *   Get Student state 
    */
    public StudentState getStudentState();

    /**
    *   Get Student ID 
    */
    public int getID();
    
    public void setID(int id);
    /**
    *   Simulate a walk to the bar of a student with a random time interval 
    */
    //public void walkABit(); 

    /**
    *   Simulate the student eating with a random time interval 
    */
    public void studentEating();

    public boolean isLastStudent();

    public void setLastStudent(boolean lastStudent);
}
