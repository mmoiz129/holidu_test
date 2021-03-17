package com.holidu.interview.assignment.models;

public class Bounds {

    private Double minX;
    private Double maxX;
    private Double minY;
    private Double maxY;

    public Bounds(Double x, Double y, Double radius) {
        this.minX = x - radius;
        this.maxX = x + radius;
        this.minY = y - radius;
        this.maxY = y + radius;
    }

    public Double getMinX() {
        return minX;
    }

    public void setMinX(Double minX) {
        this.minX = minX;
    }

    public Double getMaxX() {
        return maxX;
    }

    public void setMaxX(Double maxX) {
        this.maxX = maxX;
    }

    public Double getMinY() {
        return minY;
    }

    public void setMinY(Double minY) {
        this.minY = minY;
    }

    public Double getMaxY() {
        return maxY;
    }

    public void setMaxY(Double maxY) {
        this.maxY = maxY;
    }
}

