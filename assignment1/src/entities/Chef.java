package entities;

public class Chef extends Thread
{
    private ChefState state;

    public Chef(ChefState state)
    {
        this.state = state;
    }

    public void setState(ChefState state)
    {
        this.state = state;
    }

    public ChefState getChefState()
    {
        return this.state;
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
