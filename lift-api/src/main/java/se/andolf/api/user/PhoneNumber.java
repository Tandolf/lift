package se.andolf.api.user;

/**
 * @author Thomas on 2017-10-28.
 */
public class PhoneNumber {

    private final ContactType type;
    private final String value;

    public PhoneNumber(ContactType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
