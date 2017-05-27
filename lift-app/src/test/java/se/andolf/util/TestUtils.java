package se.andolf.util;

/**
 * Created by Thomas on 2016-08-27.
 */
public class TestUtils {

    private TestUtils() {
    }

    public static String getPatchString(String operation, String parameter, String value){
        return String.format("[{\"op\":\"%s\",\"path\" : \"/%s\", \"value\":\"%s\"}]", operation, parameter, value);
    }
}
