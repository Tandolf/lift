package se.andolf.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.TimestampSynchronizer;
import com.fasterxml.uuid.UUIDTimer;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Thomas on 2016-09-18.
 */
public class UUIDUtilsTest {

    private TimeBasedGenerator tb;

    @Before
    public void init() throws IOException {
        EthernetAddress nic = EthernetAddress.fromInterface();
        TimestampSynchronizeMock tsMock = new TimestampSynchronizeMock();
        UUIDTimer timer = new UUIDTimer(new Random(), tsMock);
        tb = new TimeBasedGenerator(nic, timer);
    }

    @Test
    public void should_return_a_uuid_as_string() {
        String result = UUIDUtils.genUUIDasString(tb);
        assertNotNull(result);
    }

    @Test
    public void should_return_a_uuid() throws IOException {
        UUID result = UUIDUtils.genUUID(tb);
        assertNotNull(result);
    }

    private class TimestampSynchronizeMock extends TimestampSynchronizer {

        @Override
        protected long initialize() throws IOException {
            return 0;
        }

        @Override
        protected void deactivate() throws IOException {

        }

        @Override
        protected long update(long now) throws IOException {
            return 0;
        }
    }
}
