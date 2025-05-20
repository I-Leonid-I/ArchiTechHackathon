package exceptions;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("Not enough money in the wallet or it is blocked.");
    }
}
