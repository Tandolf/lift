package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

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

    public UserEntity() {
        this.accountInfoEntities = new HashSet<>();
    }

    private UserEntity(String firstname, String lastname, Set<AccountInfoEntity> accountInfoEntities) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountInfoEntities = accountInfoEntities;
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

    public static class Builder {

        private String firstname;
        private String lastname;
        private Set<AccountInfoEntity> accountInfoEntities = new HashSet<>();

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

        public UserEntity build() {
            return new UserEntity(firstname, lastname, accountInfoEntities);
        }
    }
}
