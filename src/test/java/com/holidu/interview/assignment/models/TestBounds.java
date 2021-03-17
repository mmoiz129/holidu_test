package com.holidu.interview.assignment.models;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestBounds {

    @Test
    public void TestBoundValues() {
        Bounds bounds = new Bounds(5D, 10D, 20D);
        assertEquals("5 - 20 = -15", bounds.getMinX(), -15D);
        assertEquals("5 + 20 = 25", bounds.getMaxX(), 25D);
        assertEquals("10 - 20 = -10", bounds.getMinY(), -10D);
        assertEquals("10 + 20 = 30", bounds.getMaxY(), 30D);
    }

}
