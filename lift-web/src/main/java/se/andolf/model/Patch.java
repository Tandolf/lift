package se.andolf.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Thomas on 2016-10-30.
 */
public class Patch {

    @ApiModelProperty(required = true, allowableValues = "add, copy, move, remove, replace, test", example = "replace")
    private String op;

    @ApiModelProperty(required = true, notes = "name of the parameter to alter in the form of a path", example = "/parameter")
    private String path;

    @ApiModelProperty(required = true, notes = "the new value", example = "new value")
    private String value;

    public Patch(String op, String path, String value) {
        this.op = op;
        this.path = path;
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }
}
