package entities.operation;

import entities.client.Client;

public class OperationProperties {
    private final Client sender;
    private final Client receiver;

    public OperationProperties(Client sender, Client receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Client getSender() {
        return sender;
    }

    public Client getReceiver() {
        return receiver;
    }
}
