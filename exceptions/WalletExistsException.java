package exceptions;

public class WalletExistsException extends RuntimeException {
    public WalletExistsException() {
        super("The wallet already exists for this client.");
    }
}
