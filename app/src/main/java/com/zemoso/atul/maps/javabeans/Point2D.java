package com.zemoso.atul.maps.javabeans;

import org.json.JSONArray;

/**
 * Created by zemoso on 8/9/17.
 */

public class Point2D {
    private Double x;
    private Double y;

    public Point2D() {
    }

    public Point2D(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(JSONArray coordinates) {
        this.x = coordinates.optDouble(0);
        this.y = coordinates.optDouble(1);
    }

    @Override
    public String toString() {
        return "{" +
                "\"x\":" + x +
                ", \"y\":" + y +
                '}';
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
