package se.andolf.model;

/**
 * Created by Thomas on 2016-07-09.
 */
public class MessageEntity {
    private String message;

    public MessageEntity() {
    }

    public MessageEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
