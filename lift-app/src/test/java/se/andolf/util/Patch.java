package se.andolf.util;

/**
 * @author Thomas on 2016-09-04.se.andolf
 */
public class Patch {

    private final String op;
    private final String path;
    private final String value;

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
