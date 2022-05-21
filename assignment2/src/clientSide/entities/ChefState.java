package clientSide.entities;

public enum ChefState {
    
    WAITING_FOR_AN_ORDER("WAFOR"),

    PREPARING_THE_COURSE("PRPCS"),

    DISHING_THE_PORTIONS("DSHPT"),

    DELIVERING_THE_PORTIONS("DLVPT"),

    CLOSING_SERVICE("CLSSV");

    private final String description;
    
    private ChefState(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

}