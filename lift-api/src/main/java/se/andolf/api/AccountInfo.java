package se.andolf.api;

/**
 * @author Thomas on 2017-07-16.
 */
public class AccountInfo {

    private final String email;

    public AccountInfo(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder {

        private String email;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountInfo build() {
            return new AccountInfo(email);
        }
    }
}
