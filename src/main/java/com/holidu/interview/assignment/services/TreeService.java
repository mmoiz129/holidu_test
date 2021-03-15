package com.holidu.interview.assignment.services;

import com.holidu.interview.assignment.exception.HoliduErrorType;
import com.holidu.interview.assignment.exception.HoliduException;
import com.holidu.interview.assignment.models.AggregateTreeData;
import com.holidu.interview.assignment.models.TreeData;
import com.holidu.interview.assignment.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TreeService {

    @Autowired
    private HttpService httpService;

    @Value("${datasource}")
    private String dataSource;

    private final String SELECT_PARAM = "spc_common, x_sp, y_sp";

    /**
     * Fetch data from API and aggregate it
     * @param x
     * @param y
     * @param radius
     * @return
     * @throws HoliduException
     */
    public AggregateTreeData fetchTrees(Double x, Double y, Double radius) throws HoliduException {
        //validation
        if (x == null || y == null || radius == null) {
            throw new HoliduException(HoliduErrorType.REQUIRED_PARAMETER);
        }

        if (radius <= 0) {
            throw new HoliduException(HoliduErrorType.INVALID_RADIUS);
        }

        // Convert radius to foot to make calculation easy as x_sp and y_sp are in foot
        double radiusInFoot = Util.meterToFoot(radius);

        //make where params to filter data
        String whereParam = createWhereParamsForBounds(x, y, radiusInFoot);

        //create URI to fetch data
        URI uri = createURI(SELECT_PARAM, whereParam);

        //fetch data from API
        TreeData[] treeData = httpService.getRequest(uri, TreeData[].class);

        //Aggregate data
        Map<String, Integer> data = filterAndAggregateTreeData(treeData, x, y, radiusInFoot);

        return new AggregateTreeData(data);
    }

    /**
     * Create URI
     *
     * @param selectParam
     * @param whereParam
     * @return
     */
    private URI createURI(String selectParam, String whereParam) {

        return UriComponentsBuilder.fromUriString(dataSource)
                .queryParam("$select", selectParam)
                .queryParam("$where", whereParam)
                .build().toUri();
    }

    /**
     * Aggregate data using steam and map on Hashmap
     * @param treeData
     * @param x
     * @param y
     * @param radiusInFeet
     * @return
     */
    private Map<String, Integer> filterAndAggregateTreeData(TreeData[] treeData, Double x, Double y, Double radiusInFeet) {
        return Arrays.stream(treeData)
                .filter(t -> t.getSpc_common() != null && Util.withInBounds(x, y, t.getX_sp(), t.getY_sp(), radiusInFeet))
                .collect(Collectors.toConcurrentMap(w -> w.getSpc_common(), w -> 1, Integer::sum));
    }

    /**
     * Create where parameters to search the with in the radius
     *
     * @param x
     * @param y
     * @param radius
     * @return
     */
    private String createWhereParamsForBounds(Double x, Double y, Double radius) {
        //Create maximum and minimum bounds
        double maximumX = x + radius;
        double minimumX = x - radius;
        double maximumY = y + radius;
        double minimumY = y - radius;

        return "x_sp <= " + String.valueOf(maximumX) + " AND " +
                "x_sp >= " + String.valueOf(minimumX) + " AND " +
                "y_sp <= " + String.valueOf(maximumY) + " AND " +
                "y_sp >= " + String.valueOf(minimumY);

    }

}
