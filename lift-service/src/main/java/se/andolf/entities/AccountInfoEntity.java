package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author Thomas on 2017-07-16.
 */
@NodeEntity
public class AccountInfoEntity {

    @GraphId
    private Long id;

    @Index(unique = true)
    private String email;

    public AccountInfoEntity() {
    }

    public AccountInfoEntity(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class Builder {
        private Long id;
        private String email;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountInfoEntity build() {
            return new AccountInfoEntity(id, email);
        }
    }
}
