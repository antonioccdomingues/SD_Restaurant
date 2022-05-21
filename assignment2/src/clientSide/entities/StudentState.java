package clientSide.entities;

public enum StudentState {
    
    GOING_TO_THE_RESTAURANT("GGTRT"),

    TAKING_A_SEAT_AT_THE_TABLE("TKSTT"),

    SELECTING_THE_COURSES("SELCS"),

    ORGANIZING_THE_ORDER("OGODR"),

    CHATTING_WITH_COMPANIONS("CHTWC"),

    ENJOYING_THE_MEAL("EJYML"),

    PAYING_THE_BILL("PYTBL"),

    GOING_HOME("GGHOM");

    private StudentState(String description){
        this.description = description;
    }

    private final String description; 

    @Override
    public String toString() {
        // Auto-generated method stub
        return this.description;
    }
}
