package entities.operation;

import entities.client.Client;
import exceptions.BlockedReceiverException;
import exceptions.NotEnoughMoneyException;
import exceptions.OperationAlreadyDoneException;

public abstract class Operation {
    private final int id;
    private final String time;
    protected final int amount;
    protected OperationStatus status;
    protected final OperationProperties operationProperties;

    protected Operation(int id, String time, int amount, Client sender, Client receiver, OperationStatus status) {
        this.id = id;
        this.time = time;
        this.amount = amount;
        this.status = status;
        operationProperties = OperationPropertiesFactory.getOperationProperties(sender, receiver);
    }

    public abstract void performOperation() throws OperationAlreadyDoneException,
            BlockedReceiverException,
            NotEnoughMoneyException;
}
