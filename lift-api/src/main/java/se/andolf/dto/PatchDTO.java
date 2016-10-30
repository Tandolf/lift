package se.andolf.dto;

/**
 * Created by Thomas on 2016-10-30.
 */
public class PatchDTO {

    private String op;
    private String path;
    private String value;

    public PatchDTO(String op, String path, String value) {
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
