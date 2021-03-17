package com.holidu.interview.assignment.services;

import com.holidu.interview.assignment.exception.HoliduException;
import com.holidu.interview.assignment.models.AggregateTreeData;
import com.holidu.interview.assignment.models.Bounds;
import com.holidu.interview.assignment.models.TreeData;
import com.holidu.interview.assignment.utils.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import java.net.URI;
import java.util.Map;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TestTreeService {

    @InjectMocks
    private TreeService treeService;

    @Mock
    HttpService httpService;

    @Mock
    PropertiesService propService;

    @Test
    public void testCreateURIWithoutParam() {
        URI uri = treeService.createURI("http://localhost", "", "");
        assertNotNull("URI object should not be null", uri);
        assertNotNull("It should have host", uri.getHost());
        assertNull("query parameter should NOT be set", uri.getQuery());
    }

    @Test
    public void testCreateURIWithParam() {
        URI uri = treeService.createURI("http://localhost", "a", "b");
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
        TreeData[] data = new TreeData[]{
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


    @Test(expected = HoliduException.class)
    public void testFetchTreesInvalidRadius() {
        AggregateTreeData data = treeService.fetchTrees(2D, 3D, 0D);
    }

    @Test(expected = HoliduException.class)
    public void testFetchTreesNullArguments() {
        AggregateTreeData data = treeService.fetchTrees(null, 3D, 0D);
    }

    @Test
    public void testFetchTrees() {
        double x = 1, y = 1, radius = 5;
        TreeData[] treeData = new TreeData[]{
                new TreeData("a", 4D, 5D),
                new TreeData("b", 1D, 3D),
                new TreeData("b", 2D, 5D),
                new TreeData("c", -4D, 20D)
        };
        double radiusInFoot = Util.meterToFoot(radius);
        Bounds bounds = new Bounds(x, y, radiusInFoot);
        String whereParam = treeService.createWhereParamsForBounds(bounds);
        URI uri = treeService.createURI("https://data.cityofnewyork.us/resource/nwxe-4ae8.json", "spc_common, x_sp, y_sp", whereParam);
        when(httpService.getRequest(uri, TreeData[].class)).thenReturn(treeData);
        when(propService.getDatasource()).thenReturn("https://data.cityofnewyork.us/resource/nwxe-4ae8.json");

        AggregateTreeData data = treeService.fetchTrees(x, y, radius);
        assertEquals(data.getTrees().get("a"), java.util.Optional.of(1).get());
        assertEquals(data.getTrees().get("b"), java.util.Optional.of(2).get());

    }


}
