package se.andolf.model;

/**
 * Created by Thomas on 2016-07-09.
 */
public class RESTException {
    private String message;

    public RESTException() {
    }

    public RESTException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
