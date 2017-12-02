package se.andolf.api.user;

import java.util.Collections;
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
    private final List<Address> addresses;
    private final String password;
    private final String timezone;

    public User(String id, Meta meta, String userName, boolean active, Name name, List<Email> emails, List<PhoneNumber> phoneNumbers, List<Role> roles, List<Address> addresses, String password, String timezone) {
        this.id = id;
        this.meta = meta;
        this.userName = userName;
        this.active = active;
        this.name = name;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.roles = roles;
        this.addresses = addresses;
        this.password = password;
        this.timezone = timezone;
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
        if(roles == null)
            return Collections.singletonList(new Role(Roles.USER));
        return roles;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public String getPassword() {
        return password;
    }

    public String getTimezone() {
        return timezone;
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
        private List<Address> addresses;
        private String password;
        private String timeZone;

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

        public Builder addresses(List<Address> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(id, meta, userName, active, name, emails, phoneNumbers, roles, addresses, password, timeZone);
        }

        public Builder timeZone(String timezone) {
            this.timeZone = timezone;
            return this;
        }
    }
}
