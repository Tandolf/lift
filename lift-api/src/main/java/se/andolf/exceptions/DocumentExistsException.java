package se.andolf.exceptions;

/**
 * @author Thomas on 2016-06-12.
 */
public class DocumentExistsException extends RuntimeException {
    public DocumentExistsException(String message) {
        super(message);
    }

    public DocumentExistsException(Throwable cause) {
        super(cause);
    }

    public DocumentExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
