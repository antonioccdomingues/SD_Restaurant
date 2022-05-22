package serverSide.entities;

public interface Chef 
{
    public void setChefID(int id);

    public int getChefID();

    public void setState(ChefState state);

    public ChefState getChefState();

}
