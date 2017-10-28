package se.andolf.exceptions;

/**
 * @author Thomas on 2016-06-12.
 */
public class DocomentNotFoundException extends RuntimeException {

    public DocomentNotFoundException() {
    }

    public DocomentNotFoundException(String message) {
        super(message);
    }

    public DocomentNotFoundException(Throwable cause) {
        super(cause);
    }

    public DocomentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
