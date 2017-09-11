package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 8/9/17.
 */

public class GeoPoint {
    private Double lon;
    private Double lat;

    public GeoPoint() {
    }

    public GeoPoint(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public GeoPoint(JSONObject jsonObject) {
        this.lon = jsonObject.optDouble("lon");
        this.lat = jsonObject.optDouble("lat");
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "{" +
                "\"lon\":" + lon +
                ", \"lat\":" + lat +
                '}';
    }
}
