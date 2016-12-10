package se.andolf.entities;

import org.neo4j.ogm.annotation.Relationship;
import se.andolf.api.GenderType;

/**
 * Created by Thomas on 2016-06-26.
 */
public class User extends Entity {

    private String firstname;
    private String lastname;
    private String email;
    private GenderType gender;

    @Relationship(type = "MEASURMENTS", direction = Relationship.INCOMING)
    private Body body;

    public User() {
        //For Serialization
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

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        User user = (User) o;

        if (!firstname.equals(user.firstname))
            return false;
        if (!lastname.equals(user.lastname))
            return false;
        if (!email.equals(user.email))
            return false;
        if (gender != user.gender)
            return false;
        if (!body.equals(user.body))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
