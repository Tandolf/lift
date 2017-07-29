package se.andolf.api;

/**
 * @author Thomas on 2017-07-22.
 */
public class ContactInfo {

    private final String addressLine1;
    private final String addressLine2;
    private final String city;
    private final String stateOrProvince;
    private final String postalCode;
    private final String mobileNumber;


    public ContactInfo(String addressLine1, String addressLine2, String city, String stateOrProvince, String postalCode, String mobileNumber) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.postalCode = postalCode;
        this.mobileNumber = mobileNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public static class Builder {

        private String addressLine1;
        private String addressLine2;
        private String city;
        private String stateOrProvince;
        private String postalCode;
        private String mobileNumber;

        public Builder setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public Builder setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setStateOrProvince(String stateOrProvince) {
            this.stateOrProvince = stateOrProvince;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public ContactInfo build() {
            return new ContactInfo(addressLine1, addressLine2, city, stateOrProvince, postalCode, mobileNumber);
        }
    }
}
