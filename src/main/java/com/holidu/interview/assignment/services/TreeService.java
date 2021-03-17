package com.holidu.interview.assignment.services;

import com.holidu.interview.assignment.exception.HoliduErrorType;
import com.holidu.interview.assignment.exception.HoliduException;
import com.holidu.interview.assignment.models.AggregateTreeData;
import com.holidu.interview.assignment.models.Bounds;
import com.holidu.interview.assignment.models.TreeData;
import com.holidu.interview.assignment.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
     *
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
        Bounds bounds = new Bounds(x, y, radiusInFoot);
        String whereParam = createWhereParamsForBounds(bounds);

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
    public URI createURI(String selectParam, String whereParam) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(dataSource).cloneBuilder();

        if (StringUtils.hasText(selectParam)) {
            builder.queryParam("$select", selectParam);
        }

        if (StringUtils.hasText(whereParam)) {
            builder.queryParam("$where", whereParam);
        }

        return builder.build().toUri();
    }

    /**
     * Aggregate data using steam and map on Hashmap
     *
     * @param treeData
     * @param x
     * @param y
     * @param radiusInFeet
     * @return
     */
    public Map<String, Integer> filterAndAggregateTreeData(TreeData[] treeData, Double x, Double y, Double radiusInFeet) {
        return Arrays.stream(treeData)
                .filter(t -> t.getSpc_common() != null && Util.withInBounds(x, y, t.getX_sp(), t.getY_sp(), radiusInFeet))
                .collect(Collectors.toConcurrentMap(w -> w.getSpc_common(), w -> 1, Integer::sum));
    }


    /**
     * Make where parameter for bounds
     * @param bounds
     * @return
     */
    public String createWhereParamsForBounds(Bounds bounds) {

        //Create maximum and minimum bounds params
        return "x_sp <= " + String.valueOf(bounds.getMaxX()) + " AND " +
                "x_sp >= " + String.valueOf(bounds.getMinX()) + " AND " +
                "y_sp <= " + String.valueOf(bounds.getMaxY()) + " AND " +
                "y_sp >= " + String.valueOf(bounds.getMinY());

    }

}
