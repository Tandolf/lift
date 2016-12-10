package se.andolf.model;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Thomas on 2016-06-21.
 */
public class Category extends Entity {

    @NotNull
    @Size(min=2, max=30)
    @ApiModelProperty(required = true)
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
