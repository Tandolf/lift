package se.andolf.dto;

import se.andolf.api.GenderType;

/**
 * Created by Thomas on 2016-07-03.
 */
public class UserDTO extends BaseDTO {

    private String firstname;
    private String lastname;
    private GenderType gender;
    private String email;
    private BodyDTO bodyDTO;

    public UserDTO() {
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

    public BodyDTO getBodyDTO() {
        return bodyDTO;
    }

    public void setBodyDTO(BodyDTO bodyDTO) {
        this.bodyDTO = bodyDTO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
