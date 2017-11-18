package se.andolf.api.user;

/**
 * @author Thomas on 2017-10-28.
 */
public class Name {

    private final String formatted;
    private final String givenName;
    private final String familyName;

    public Name(String givenName, String familyName) {
        this.formatted = givenName + " " + familyName;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public String getFormatted() {
        return formatted;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public static class Builder {
        private String givenName;
        private String familyName;

        public Builder givenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Name build() {
            return new Name(givenName, familyName);
        }
    }
}
