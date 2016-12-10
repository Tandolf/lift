package se.andolf.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Thomas on 2016-06-18.
 */
public class Equipment extends Entity {

    @NotNull
    @Size(min=2, max=32)
    private String name;

    public Equipment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
