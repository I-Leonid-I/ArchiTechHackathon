package entities.wallet;

public class Wallet {
    private final int walletId;
    private int balance;
    private boolean active;

    public Wallet(int walletId, int balance, boolean active) {
        this.walletId = walletId;
        this.balance = balance;
        this.active = active;
    }

    public int getWalletId() {
        return walletId;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int amount) {
        this.balance += amount;
    }

    public void subtractBalance(int amount) {
        this.balance -= amount;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isCredit() {
        return this.balance < 0;
    }

    public int creditValue() {
        return this.balance < 0 ? -this.balance : 0;
    }
}
