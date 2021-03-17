package com.holidu.interview.assignment.services;


import com.holidu.interview.assignment.models.Bounds;
import com.holidu.interview.assignment.models.TreeData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Map;

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

    @Test
    public void testFilterAndAggregateData() {
        TreeData[] data = new TreeData[] {
                new TreeData("a", 4D, 5D),
                new TreeData("b", 1D, 3D),
                new TreeData("b", 2D, 5D),
                new TreeData("c", -4D, 9D)
        };

        Map<String, Integer> map = treeService.filterAndAggregateTreeData(data, 1D, 1D, 5D);
        assertEquals("A count should be 1", map.get("a"), java.util.Optional.of(1).get());
        assertEquals("B count should be 2", map.get("b"), java.util.Optional.of(2).get());
        assertEquals("C should be filter out ", map.get("c"), null);

    }



}
