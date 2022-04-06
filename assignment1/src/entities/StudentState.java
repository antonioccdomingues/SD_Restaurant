package entities;

public enum StudentState {
    
    GOING_TO_THE_RESTAURANT("GTTRTNT"),

    TAKING_A_SEAT_AT_THE_TABLE("TKASATTBL"),

    SELECTING_THE_COURSES("SLTCRS"),

    ORGANIZING_THE_ORDER("OZTHOD"),

    CHATTING_WITH_COMPANIONS("CTWCPN"),

    ENJOYING_THE_MEAL("EJTHM"),

    PAYING_THE_MEAL("PYTHM"),

    GOING_HOME("GHM");

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
