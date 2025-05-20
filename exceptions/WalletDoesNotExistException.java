package exceptions;

public class WalletDoesNotExistException extends RuntimeException {
    public WalletDoesNotExistException() {
        super("The wallet does not exist for this client or deactivated.");
    }
}
