package se.andolf.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Thomas on 2017-07-16.
 */
@Document(collection = "Account")
public class AccountInfoEntity {

    @Id
    private Long id;

    @Indexed(unique = true)
    private String email;

    @PersistenceConstructor
    public AccountInfoEntity(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
