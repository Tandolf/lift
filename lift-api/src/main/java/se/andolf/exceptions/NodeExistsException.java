package se.andolf.exceptions;

/**
 * Created by Thomas on 2016-06-12.
 */
public class NodeExistsException extends RuntimeException {
    public NodeExistsException(String message) {
        super(message);
    }

    public NodeExistsException(Throwable cause) {
        super(cause);
    }

    public NodeExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
