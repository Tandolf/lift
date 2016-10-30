package se.andolf.util;

import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

/**
 * Created by Thomas on 2016-07-09.
 */
public class UUIDUtils {

    private UUIDUtils() {
    }

    public static String genUUIDasString(TimeBasedGenerator generator){
        final UUID uuid = generator.generate();
        final StringBuilder sb = new StringBuilder();
        return sb.append(Long.toHexString(uuid.getMostSignificantBits())).append(Long.toHexString(uuid.getLeastSignificantBits())).toString();
    }

    public static UUID genUUID(TimeBasedGenerator generator){
        return generator.generate();
    }
}
