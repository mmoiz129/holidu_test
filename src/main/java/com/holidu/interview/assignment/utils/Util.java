package com.holidu.interview.assignment.utils;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

public class Util {

    /**
     * Convert meter to foot
     *
     * @param meter
     * @return
     */
    public static double meterToFoot(Double meter) {
        return meter * 3.28;
    }

    /**
     * Check if the point is within the radius bounds
     *
     * @param x
     * @param y
     * @param maxX
     * @param maxY
     * @param radius
     * @return
     */
    public static boolean withInBounds(double x, double y, double maxX, double maxY, double radius) {
        double distance = Math.sqrt(Math.pow((maxX - x), 2) + Math.pow((maxY - y), 2));
        return distance <= radius;
    }

}
