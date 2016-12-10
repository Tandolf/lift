package se.andolf.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Thomas on 2016-06-25.
 */
public abstract class Entity {

    @ApiModelProperty(readOnly = true)
    private String uniqueId;

    public Entity() {
    }

    public Entity(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
