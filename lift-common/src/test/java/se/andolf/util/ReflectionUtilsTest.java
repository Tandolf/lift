package se.andolf.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Thomas on 2016-08-28.
 */
public class ReflectionUtilsTest {

    @Test
    public void should_set_private_field_value(){
        String value = "test";
        TestClass testClass = new TestClass();
        ReflectionUtils.setField(testClass, "value", String.class, value);
        Assert.assertEquals(value, testClass.getValue());
    }

    public class TestClass {
        private String value;

        public String getValue() {
            return value;
        }
    }
}
