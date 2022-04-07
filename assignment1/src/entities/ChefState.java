package entities;

public enum ChefState {
    
    WAITING_FOR_AN_ORDER("WFAO"),

    PREPARING_THE_COURSE("PPTC"),

    DISHING_THE_PORTIONS("DSPT"),

    DELIVERING_THE_PORTIONS("DTPT"),

    CLOSING_SERVICE("CLSV");

    private final String description;
    
    private ChefState(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

}