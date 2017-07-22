package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Thomas on 2017-07-16.
 */
@NodeEntity
public class UserEntity {

    @GraphId
    private Long id;
    private String firstname;
    private String lastname;

    @Relationship(type = "HAS_ACCOUNT_INFO")
    private Set<AccountInfoEntity> accountInfoEntities;

    @Relationship(type = "HAS_CONTACT_INFO")
    private Set<ContactInfoEntity> contactInfoEntities;

    public UserEntity() {
        this.accountInfoEntities = new HashSet<>();
        this.contactInfoEntities = new HashSet<>();
    }

    private UserEntity(String firstname, String lastname, Set<AccountInfoEntity> accountInfoEntities, Set<ContactInfoEntity> contactInfoEntities) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountInfoEntities = accountInfoEntities;
        this.contactInfoEntities = contactInfoEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void addAccountInfo(AccountInfoEntity accountInfoEntity) {
        accountInfoEntities.add(accountInfoEntity);
    }

    public void addContactInfo(ContactInfoEntity contactInfoEntity) {
        contactInfoEntities.add(contactInfoEntity);
    }

    public Set<AccountInfoEntity> getAccountInfoEntities() {
        return accountInfoEntities;
    }

    public Set<ContactInfoEntity> getContactInfoEntities() {
        return contactInfoEntities;
    }

    public static class Builder {

        private String firstname;
        private String lastname;
        private Set<AccountInfoEntity> accountInfoEntities = new HashSet<>();
        private Set<ContactInfoEntity> contactInfoEntities = new HashSet<>();


        public Builder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder addAccountInfo(AccountInfoEntity accountInfoEntity) {
            accountInfoEntities.add(accountInfoEntity);
            return this;
        }

        public Builder addContactInfo(ContactInfoEntity contactInfoEntity) {
            contactInfoEntities.add(contactInfoEntity);
            return this;
        }

        public UserEntity build() {
            return new UserEntity(firstname, lastname, accountInfoEntities, contactInfoEntities);
        }
    }
}
