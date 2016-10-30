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
}
