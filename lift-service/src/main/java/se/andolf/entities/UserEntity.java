package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import se.andolf.api.user.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Thomas on 2017-07-16.
 */
@Document(collection = "User")
public class UserEntity {

    @Id
    private String id;
    private MetaEntity meta;
    private String userName;
    private boolean active;
    private Name name;
    private List<Email> emails;
    private List<PhoneNumber> phoneNumbers;
    private List<Role> roles;
    private List<Address> addresses;
    private final String password;
    private String timezone;

    @PersistenceConstructor
    private UserEntity(MetaEntity meta, String userName, boolean active, Name name, List<Email> emails, List<PhoneNumber> phoneNumbers, List<Role> roles, List<Address> addresses, String password) {
        this.meta = meta;
        this.userName = userName;
        this.active = active;
        this.name = name;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.roles = roles;
        this.addresses = addresses;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public MetaEntity getMeta() {
        return meta;
    }

    public String getUserName() {
        return userName;
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

    public boolean isActive() {
        return active;
    }

    public List<Role> getRoles() {
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

        private String userName;
        private boolean active;
        private Name name;
        private List<Email> emails;
        private List<PhoneNumber> phoneNumbers;
        private List<Role> roles;
        private MetaEntity meta;
        private List<Address> addresses;
        private String password;

        public Builder meta(MetaEntity meta) {
            this.meta = meta;
            return this;
        }

        public Builder userName(String userName) {
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

        public UserEntity build() {
            return new UserEntity(meta, userName, active, name, emails, phoneNumbers, roles, addresses, password);
        }
    }
}
