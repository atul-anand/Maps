package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 8/9/17.
 */

public class GeoCircle {
    private String type;
    private Point2D coordinates;
    private String radius;

    public GeoCircle(JSONObject jsonObject) {
        this.type = jsonObject.optString("type");
        this.coordinates = new Point2D(jsonObject.optJSONArray("coordinates"));
        this.radius = jsonObject.optString("radius");
    }

    public GeoCircle(String type, Point2D coordinates, String radius) {
        this.type = type;
        this.coordinates = coordinates;
        this.radius = radius;
    }

    public GeoCircle() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Point2D getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point2D coordinates) {
        this.coordinates = coordinates;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + type + '\"' +
                ", \"coordinates\":" + coordinates +
                ", \"radius\":\"" + radius + '\"' +
                '}';
    }
}
