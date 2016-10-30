package se.andolf.util;

/**
 * Created by Thomas on 2016-09-04.
 */
public enum PatchOperations {
    ADD("add"), REMOVE("remove"), REPLACE("replace"), MOVE("move"), COPY("copy");

    private String name;

    PatchOperations(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
