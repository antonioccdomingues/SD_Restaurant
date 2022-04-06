package entities;

public enum WaiterState {
    
    APPRAISING_SITUATION("APRSTT"),

    PRESENTING_THE_MENU("PTTMN"),

    TAKING_THE_ORDER("TKTHOD"),

    PLACING_THE_ORDER("PLTHOD"),

    WAITING_FOR_PORTION("WTFPT"),

    PROCESSING_THE_BILL("PCTHBL"),

    RECEIVING_PAYMENT("RCVPMT");


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