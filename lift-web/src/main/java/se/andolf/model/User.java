package se.andolf.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import se.andolf.api.GenderType;

import javax.validation.constraints.NotNull;

/**
 * Created by Thomas on 2016-06-26.
 */
public class User extends Entity {

    @NotNull
    @Length(min = 2, max = 64)
    private String firstname;

    @NotNull
    @Length(min = 2, max = 64)
    private String lastname;

    @NotNull
    private GenderType gender;

    @NotNull
    @Length(min = 2, max = 64)
    @Email
    private String email;

    @NotNull
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
