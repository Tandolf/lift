package se.andolf.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Thomas on 2016-06-21.
 */
public class RESTCategory extends RESTEntity {

    @NotNull
    @Size(min=2, max=30)
    private String name;

    public RESTCategory(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
