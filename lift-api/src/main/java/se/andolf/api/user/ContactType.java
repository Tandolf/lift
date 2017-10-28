package se.andolf.api.user;

/**
 * @author Thomas on 2017-10-28.
 */
public enum ContactType {
    WORK("work"),
    HOME("home"),
    MOBILE("mobile"),
    PRIVATE("private");

    private final String name;

    ContactType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
