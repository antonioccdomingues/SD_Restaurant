package entities;

public enum WaiterState {
    
    APPRAISING_SITUATION("APPST"),

    PRESENTING_THE_MENU("PRSMN"),

    TAKING_THE_ORDER("TKODR"),

    PLACING_THE_ORDER("PCODR"),

    WAITING_FOR_PORTION("WTFPT"),

    PROCESSING_THE_BILL("PRCBL"),

    RECEIVING_PAYMENT("RECPM");

    private WaiterState(String description){
        this.description = description;
    }
    private final String description;

    @Override
    public String toString() {
        // Auto-generated method stub
        return this.description;
    }
}