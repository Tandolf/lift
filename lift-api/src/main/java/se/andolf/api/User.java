package se.andolf.api;

/**
 * @author Thomas on 2017-07-16.
 */
public class User {

    private final long id;
    private final String firstname;
    private final String lastname;
    private final AccountInfo accountInfo;

    public User(long id, String firstname, String lastname, AccountInfo accountInfo) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountInfo = accountInfo;
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

    public AccountInfo getAccountInfo() {
        return accountInfo;
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
            return new User(id, firstname, lastname, null);
        }
    }
}
