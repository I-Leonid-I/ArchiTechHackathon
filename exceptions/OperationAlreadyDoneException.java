package exceptions;

import entities.operation.OperationStatus;

public class OperationAlreadyDoneException extends RuntimeException {
    public OperationAlreadyDoneException(OperationStatus status) {
        super("Operation already done: " + status.getStatus());
    }
}
