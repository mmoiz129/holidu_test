package com.holidu.interview.assignment.utils;

public class Util {

    /**
     * Convert meter to foot
     *
     * @param meter
     * @return
     */
    public static double meterToFoot(Double meter) {
        return meter * 3.281;
    }

    /**
     * Check if the point is within the radius bounds
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
