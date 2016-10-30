package se.andolf.api;

/**
 * Created by Thomas on 2016-06-26.
 */
public enum GenderType {
    MALE("male"), FEMALE("female"), UNDEFINED("undefined");

    private String value;

    GenderType(String value) {
        this.value = value;
    }
}
