package entities;

public enum WaiterState {
    
    APPRAISING_SITUATION("APST"),

    PRESENTING_THE_MENU("PTMN"),

    TAKING_THE_ORDER("TTOD"),

    PLACING_THE_ORDER("PTOD"),

    WAITING_FOR_PORTION("WFPT"),

    PROCESSING_THE_BILL("PTBL"),

    RECEIVING_PAYMENT("RCPT");

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