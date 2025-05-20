package entities.operation;

public enum OperationStatus {
    CANCELLED("cancelled"),
    IN_PROCESS("processing"),
    SUCCESSFUL("successful");

    private final String status;

    OperationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
