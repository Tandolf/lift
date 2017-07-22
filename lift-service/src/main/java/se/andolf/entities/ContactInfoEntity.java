package se.andolf.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author Thomas on 2017-07-22.
 */
@NodeEntity
public class ContactInfoEntity {

    @GraphId
    private Long id;
    private String addressLine1;

    public ContactInfoEntity() {
    }

    public ContactInfoEntity(Long id, String addressLine1) {
        this.id = id;
        this.addressLine1 = addressLine1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public static class Builder {

        private Long id;
        private String addressLine1;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public ContactInfoEntity build() {
            return new ContactInfoEntity(id, addressLine1);
        }
    }
}
