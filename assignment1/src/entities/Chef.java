package entities;

import genclass.GenericIO;

public class Chef 
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

    public ChefState getState()
    {
        return this.state;
    }

    @Override
    public String toString() 
    {
        return this.state.toString();
    }
}
