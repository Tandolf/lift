package se.andolf.api.user;

/**
 * @author Thomas on 2017-10-28.
 */
public class Email {

    private final ContactType type;
    private final String value;
    private final boolean primary;

    public Email(ContactType type, String value, boolean primary) {
        this.type = type;
        this.value = value;
        this.primary = primary;
    }

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isPrimary() {
        return primary;
    }
}
