package serverSide.entities;

import commInfra.Message;
import commInfra.ServerCom;
import serverSide.sharedRegions.SharedRegionInterface;

public class ServiceProviderAgent extends Thread implements Chef, Waiter, Student
{
   /**
    *  Communication channel.
    */

    private ServerCom com;
    
    /**
     *  Reference to the provided service.
     */
    
    private SharedRegionInterface shi;

   /**
    *  Service to be provided.
    */

   /**
    *  Instantiation.
    *
    *     @param com communication channel
    *     @param shi reference to provided service
    */

    public ServiceProviderAgent (ServerCom com, SharedRegionInterface shi)
    {
       this.com = com;
       this.shi = shi;
    }

   /**
    *  Life cycle of the service provider agent.
    */

    @Override
    public void run ()
    {

       /* service providing */
       Message message = (Message) com.readObject();
       message = shi.processAndReply(message);
       if (message != null) {
    	   com.writeObject(message);
       }
       
    }

    /*
    * STUDENT ATRIBUTES AND METHODS
    *
    */

    /**
     * 
    *       Student State
    */
    private StudentState studentState;
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

    public boolean isLastStudent() {
        return lastStudent;
    }

    public void setLastStudent(boolean lastStudent) {
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
    public void setState(StudentState state)
    {
        this.studentState = state;
    }

    /**
    *   Get Student state 
    */
    public StudentState getStudentState()
    {
        return this.studentState; 
    }

    /**
    *   Get Student ID 
    */
    public int getID()
    {
        return this.sID;
    }

    public void setID(int id)
    {
        this.sID = id;
    }

    public void studentEating()
    {
        try {
            sleep((long) (1 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    * Waiter ATRIBUTES AND METHODS
    *
    */

    /**
    *       Waiter id
    */
    private int waiterID;

    /**
    *       Waiter State
    */
    private WaiterState waiterState;
    /**
    *       Flag to determine if the waiter can close the restaurant or not
    */
    private boolean canGoHome = false;

    public int getWaiterID() {
        return waiterID;
    }

    public void setWaiterID(int waiterID) {
        this.waiterID = waiterID;
    }

    public boolean CanGoHome() {
        return canGoHome;
    }

    public void setCanGoHome() {
        this.canGoHome = true;
    }

    public void setState(WaiterState state)
    {
        this.waiterState = state;
    }

    public WaiterState getWaiterState()
    {
        return this.waiterState;
    }

    /*
    * Chef ATRIBUTES AND METHODS
    *
    */

    private int chefID;

    private ChefState chefState;

    public int getChefID() {
        return chefID;
    }

    public void setChefID(int chefID) {
        this.chefID = chefID;
    }

    public void setState(ChefState state)
    {
        this.chefState = state;
    }

    public ChefState getChefState()
    {
        return this.chefState;
    }
}