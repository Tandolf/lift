package se.andolf.util;

import java.lang.reflect.Field;

/**
 * Created by Thomas on 2016-07-09.
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static void setField(Object entity, String fieldName, Class paramType, Object value){
        final Field field = org.springframework.util.ReflectionUtils.findField(entity.getClass(), fieldName, paramType);
        field.setAccessible(true);
        org.springframework.util.ReflectionUtils.setField(field, entity, value);
    }
}
