package se.andolf.api;

/**
 * @author Thomas on 2017-07-16.
 */
public class User {

    private final long id;
    private final String firstname;
    private final String lastname;

    public User(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public static class Builder {

        private long id;
        private String firstname;
        private String lastname;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public User build() {
            return new User(id, firstname, lastname);
        }
    }
}
