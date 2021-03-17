package com.holidu.interview.assignment.services;


import com.holidu.interview.assignment.models.Bounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static junit.framework.TestCase.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTreeService {

    @Autowired
    private TreeService treeService;

    @Test
    public void testCreateURIWithoutParam() {
        URI uri = treeService.createURI("", "");
        assertNotNull("URI object should not be null", uri);
        assertNotNull("It should have host", uri.getHost());
        assertNull("query parameter should NOT be set", uri.getQuery());
    }

    @Test
    public void testCreateURIWithParam() {
        URI uri = treeService.createURI("a", "b");
        assertNotNull("URI object should not be null", uri);
        assertNotNull("It should have host", uri.getHost());
        assertNotNull("query parameter should be set", uri.getQuery());
    }

    @Test
    public void testCreateWhereParams() {
        String result = "x_sp <= 25.0 AND x_sp >= -15.0 AND y_sp <= 30.0 AND y_sp >= -10.0";
        Bounds bounds = new Bounds(5D, 10D, 20D);
        String whereParam = treeService.createWhereParamsForBounds(bounds);
        assertEquals("Param should be equal to ", whereParam, result);
    }



}
