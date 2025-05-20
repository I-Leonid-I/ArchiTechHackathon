package exceptions;
import entities.client.Client;

public class BlockedReceiverException extends RuntimeException {
    public BlockedReceiverException() {
        super("Receiver's wallet has blocked or inactive wallet!");
    }
}
