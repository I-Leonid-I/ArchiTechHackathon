package entities.client;

import entities.wallet.Wallet;
import exceptions.WalletDoesNotExistException;
import exceptions.WalletExistsException;

public class Client {
    private final int clientId;
    private final String name;
    private final int bankId;
    private final ClientType clientType;
    private Wallet wallet;

    public Client(int clientId, String name, int bankId, String clientType) {
        this.clientId = clientId;
        this.name = name;
        this.bankId = bankId;
        this.clientType = ClientType.valueOf(clientType.toUpperCase());
    }

    public void createWallet(int walletId, int balance) throws WalletExistsException {
        if (wallet != null) {
            throw new WalletExistsException();
        }
        this.wallet = new Wallet(walletId, balance, true);
    }

    public Wallet getWallet() throws WalletDoesNotExistException {
        if (wallet == null || !wallet.isActive()) {
            throw new WalletDoesNotExistException();
        }
        return wallet;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public int getBankId() {
        return bankId;
    }

    public String getName() {
        return name;
    }

    public int getClientId() {
        return clientId;
    }
}
