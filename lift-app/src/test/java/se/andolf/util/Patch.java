package se.andolf.util;

/**
 * Created by Thomas on 2016-09-04.
 */
public class Patch {

    private String op;
    private String path;
    private String value;

    public Patch(PatchOperations op, String path, String value) {
        this.op = op.getName();
        this.path = path;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{\"op\":\"%s\",\"path\" : \"/%s\", \"value\":\"%s\"}", op, path, value);

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
