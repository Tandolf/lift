package se.andolf.api;

/**
 * @author Thomas on 2017-07-16.
 */
public class User {

    private final String firstname;
    private final String lastname;

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
