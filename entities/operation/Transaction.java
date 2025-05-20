package entities.operation;

import entities.client.Client;
import entities.wallet.Wallet;
import exceptions.BlockedReceiverException;
import exceptions.NotEnoughMoneyException;
import exceptions.OperationAlreadyDoneException;

public class Transaction extends Operation {
    public Transaction(int id, String time, int amount, Client sender, Client receiver, OperationStatus status) {
        super(id, time, amount, sender, receiver, status);
    }

    @Override
    public void performOperation() throws OperationAlreadyDoneException,
            BlockedReceiverException,
            NotEnoughMoneyException {
        if(super.status != OperationStatus.IN_PROCESS) {
            throw new OperationAlreadyDoneException(super.status);
        }

        Wallet receiverWallet = super.operationProperties.getReceiver().getWallet();

        if(!receiverWallet.isActive()) {
            throw new BlockedReceiverException();
        }

        if (super.amount > super.operationProperties.getSender().getWallet().getBalance()) {
            throw new NotEnoughMoneyException();
        }

        receiverWallet.addBalance(super.amount);
        super.operationProperties.getSender().getWallet().subtractBalance(super.amount);
    }
}
