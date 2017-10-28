package se.andolf.api.user;

import java.util.List;

/**
 * @author Thomas on 2017-07-16.
 */
public class User {

    private final String id;
    private final String userName;
    private final boolean active;
    private final Meta meta;
    private final Name name;
    private final List<Email> emails;
    private final List<PhoneNumber> phoneNumbers;
    private final List<Role> roles;

    public User(String id, Meta meta, String userName, boolean active, Name name, List<Email> emails, List<PhoneNumber> phoneNumbers, List<Role> roles) {
        this.id = id;
        this.meta = meta;
        this.userName = userName;
        this.active = active;
        this.name = name;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public Meta getMeta() {
        return meta;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isActive() {
        return active;
    }

    public Name getName() {
        return name;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public static class Builder {

        private String id;
        private Meta meta;
        private String userName;
        private boolean active;
        private Name name;
        private List<Email> emails;
        private List<PhoneNumber> phoneNumbers;
        private List<Role> roles;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder meta(Meta meta) {
            this.meta = meta;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder isActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        public Builder emails(List<Email> emails) {
            this.emails = emails;
            return this;
        }

        public Builder phoneNumbers(List<PhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        public Builder roles(List<Role> roles) {
            this.roles = roles;
            return this;
        }

        public User build() {
            return new User(id, meta, userName, active, name, emails, phoneNumbers, roles);
        }
    }
}
