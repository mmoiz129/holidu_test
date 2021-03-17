package com.holidu.interview.assignment.utils;
import org.junit.Test;

import java.net.URI;

import static junit.framework.TestCase.*;

public class UtilTest {

    @Test
    public void testMeterToFoot() {
        double foot = Util.meterToFoot(100D);
        assertEquals(foot, 328.0);
    }

    @Test
    public void pointWithinBounds() {
        boolean result = Util.withInBounds(1, 1, 3, 4, 5);
        assertTrue(result);
    }

    @Test
    public void pointOutsideBounds() {
        boolean result = Util.withInBounds(1, 1, 6, 5, 5);
        assertFalse(result);
    }

}
