package se.andolf.util;

import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Thomas on 2016-07-03.
 */
public class MappingUtils {

    private MappingUtils() {
    }

    public static <T>Type getTypeAsList(Class<T> t){
        return new TypeToken<List<T>>() {}.getType();
    }
}
