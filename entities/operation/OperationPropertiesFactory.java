package entities.operation;

import entities.client.Client;

import java.util.List;

public class OperationPropertiesFactory {
    private static List<OperationProperties> operationPropertiesList;

    public static OperationProperties getOperationProperties(Client sender, Client receiver) {
        for(OperationProperties operationProperties : operationPropertiesList) {
            if (operationProperties.getSender().equals(sender) &&
                operationProperties.getReceiver().equals(receiver)) {
                return operationProperties;
            }
        }
        OperationProperties newOperationProperties = new OperationProperties(sender, receiver);
        operationPropertiesList.add(newOperationProperties);
        return newOperationProperties;
    }
}
