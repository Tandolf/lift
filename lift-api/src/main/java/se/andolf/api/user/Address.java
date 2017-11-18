package se.andolf.api.user;

/**
 * @author Thomas on 2017-10-29.
 */
public class Address {

    private final ContactType type;
    private final String streetAddress;
    private final String locality;
    private final String postalCode;
    private final String country;
    private final boolean primary;

    public Address(ContactType type, String streetAddress, String locality, String postalCode, String country, boolean primary) {
        this.type = type;
        this.streetAddress = streetAddress;
        this.locality = locality;
        this.postalCode = postalCode;
        this.country = country;
        this.primary = primary;
    }

    public ContactType getType() {
        return type;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getLocality() {
        return locality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public boolean getPrimary() {
        return primary;
    }
}
