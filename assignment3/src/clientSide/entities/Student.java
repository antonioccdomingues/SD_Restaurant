package clientSide.entities;

import java.rmi.RemoteException;

import commInfra.ReturnValue;
import clientSide.main.Constants;
import interfaces.TableInterface;
import interfaces.BarInterface;

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
    private BarInterface bar;
    /**
    *       Reference to the shared region "Table"
    */
    private TableInterface table;

    /**
    *   Instantiation of a Student thread.
    *
    *     @param id thread number
    *     @param state student state
    *     @param bar reference to the bar 
    *     @param table reference to the table
    */
    public Student(int id, int state, BarInterface bar, TableInterface table)
    {
        this.sID=id;
        this.state = state;
        this.bar = bar;
        this.table = table;
    }

    /**
     *   Verify if LastStudent.
     *
     */

    public boolean isLastStudent()
    {
        return lastStudent;
    }

    /**
     *   Set the last student.
     *
     *     @param lastStudent true or false
     */

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
    
    public void setID(int id)
    {
        this.sID = id;
    }

    /**
    *   Life Cycle of the Student
    */
    @Override
    public void run()
    {
        walkABit();
        enter(this.sID);
        readTheMenu(this.sID);

        if(FirstStudent(this.sID))
        {
            prepareTheOrder(this.sID);
            while(!hasEverybodyChosen())
            {
                addUpOnesChoice(this.sID);
            }
            // Means the first student has registered everybody choice
            callTheWaiter(this.sID);
            System.out.printf("Student[%d] called the waiter\n", this.sID);
            describeTheOrder(this.sID);
            System.out.printf("Student[%d] described the order\n", this.sID);
            joinTheTalk(this.sID);
            System.out.printf("Student[%d] joined the talk\n", this.sID);
        }
        else
        {
            informCompanion(this.sID);
            System.out.printf("Student[%d] informed companion\n", this.sID);
        }

        
        System.out.printf("Student[%d] started eating ", this.sID);
        int courses = 0;
        while(courses < Constants.courses_number)//while(!table.isOrderDone());
        {
            // wait while it's not served
            waitingToBeServed(this.sID);
            startEating(this.sID);
            endEating(this.sID);
            //System.out.printf("Student[%d] finshed eating\n", this.sID);

            hasEverbodyFinished();
            signalTheWaiter(this.sID, this.lastStudent);
            courses++;
        }

        if(shouldHaveArrivedEarlier(this.sID))
        {
            honourTheBill();
            System.out.printf("Student[%d] payed the bill !!!\n", this.sID);
        }

        exit(this.sID);
        System.out.printf("\033[41m Student[ " + this.sID + "] End Of Life \033[0m\n");
    }    
    
    /**
    *   Simulate a walk to the bar of a student with a random time interval 
    */
    public void walkABit() 
    {
        try {
            sleep((long) (3 + 1000 * Math.random()));
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
            sleep((long) (5 + 100 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * transitions the Student from the 'going to the restaurant' state to the 'taking a seat at the table' state
     *     
     */

    private void enter(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = bar.enter(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Student from the 'taking a seat at the table' state to the 'selecting the courses' state 
     *     
     */

    private void readTheMenu(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = bar.readTheMenu(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * return true if is the first student to arrive at the restaurant
     *     
     */

    private boolean FirstStudent(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = bar.FirstStudent(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
        return ret.getBooleanValue();
    }

    /**
     * transitions the Student from the 'selecting the courses' state to the 'organizing the order' state 
     *     
     */

    private void prepareTheOrder(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = table.prepareTheOrder(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * return true if all students has chosen what to eat 
     *     
     */

    private boolean hasEverybodyChosen() {
    	ReturnValue ret = null;
    	try
        { ret = table.hasEverybodyChosen();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
        return ret.getBooleanValue();
    }

    /**
     * transitions the Student from the 'organizing the order' state to the 'organizing the order' state untill every students select what to eat
     *     
     */

    private void addUpOnesChoice(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = table.addUpOnesChoice(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * wake up the waiter while organizing the order
     *     
     */

    private void callTheWaiter(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = bar.callTheWaiter(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }  
    
    /**
     * describing the order to the waiter but remaining at the same state
     *     
     */

    private void describeTheOrder(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = table.describeTheOrder(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }  

    /**
     * transitions the Student from the 'organizing the order' state to the 'chatting with companions' state
     *     
     */

    private void joinTheTalk(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = table.joinTheTalk(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Student from the 'selecting the courses' state to the 'chatting with companions' state 
     *     
     */

    private void informCompanion(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = table.informCompanion(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * Student waites to be served
     *     
     */

    private void waitingToBeServed(int sID) {
    	try
        {
            table.waitingToBeServed(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    }

    /**
     * transitions the Student from the 'chatting with companions' state to the 'enjoying the meal' state 
     *     
     */

    private void startEating(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = table.startEating(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();

        // Simulate student eating
        this.studentEating();
    }

    /**
     * transitions the Student from the 'enjoying the meal' state to the 'chatting with companions' state 
     *     
     */

    private void endEating(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = table.endEating(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();

        // means he was the last to eat
        if(ret.getBooleanValue() == true)
        {
            this.setLastStudent(true);
        }
    }

    /**
     * transitions the Student from the 'chatting with companions' state to the 'chatting with companions' state 
     *     
     */

    private void hasEverbodyFinished() {
    	ReturnValue ret = null;
    	try
        { ret = table.hasEverbodyFinished();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }

    /**
     * transitions the Student from the 'chatting with companions' state to the 'chatting with companions' state and wakes up the waiter from appraising situation
     *     
     */

    private void signalTheWaiter(int sID, boolean last) {
    	ReturnValue ret = null;
    	try
        { ret = bar.signalTheWaiter(sID, last);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();

        // means he was the last to eat, and need to have it's last flag reset
        if(ret.getBooleanValue() == true)
        {
            this.setLastStudent(false);
        }
    }

    /**
     * transitions the Student from the 'chatting with companions' state to the 'paying the bill' state and wakes up the waiter from appraising situation
     *     
     */

    private boolean shouldHaveArrivedEarlier(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = bar.shouldHaveArrivedEarlier(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
        return ret.getBooleanValue();
    }

    /**
     * Students honours the bill
     *     
     */

    private void honourTheBill() {
    	try
        {
             table.honourTheBill();
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    }

    /**
     * transitions the Student from the 'paying the bill' state to the 'going home' state 
     *     
     */

    private void exit(int sID) {
    	ReturnValue ret = null;
    	try
        { ret = bar.exit(sID);
        }
        catch (RemoteException e)
        { 
          System.exit (1);
        }
    	this.state = ret.getStateValue();
    }
    
}
