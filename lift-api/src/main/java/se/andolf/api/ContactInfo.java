package se.andolf.api;

/**
 * @author Thomas on 2017-07-22.
 */
public class ContactInfo {

    private final String addressLine1;

    public ContactInfo(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public static class Builder {

        private String addressLine1;

        public Builder setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public ContactInfo build() {
            return new ContactInfo(addressLine1);
        }
    }
}
