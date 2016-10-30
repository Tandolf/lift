package se.andolf.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thomas on 2016-07-09.
 */
public class AssertionUtils {

    private AssertionUtils() {
    }

    public static <T, U> boolean equal(List<T> list1, List<T> list2, Function<T, U> mapper) {
        List<U> first = list1.stream().map(mapper).collect(Collectors.toList());
        List<U> second = list2.stream().map(mapper).collect(Collectors.toList());
        return first.equals(second);
    }
}
