package entities;

public enum StudentState {
    
    GOING_TO_THE_RESTAURANT("GTTR"),

    TAKING_A_SEAT_AT_THE_TABLE("TKAS"),

    SELECTING_THE_COURSES("STCR"),

    ORGANIZING_THE_ORDER("OTOD"),

    CHATTING_WITH_COMPANIONS("CWCP"),

    ENJOYING_THE_MEAL("ETML"),

    PAYING_THE_MEAL("PTML"),

    GOING_HOME("GOHM");

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
