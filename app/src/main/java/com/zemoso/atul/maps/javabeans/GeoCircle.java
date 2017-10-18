package com.zemoso.atul.maps.javabeans;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zemoso on 8/9/17.
 */

public class GeoCircle {
    private String type;
    private LatLng coordinates;
    private String radius;


    public GeoCircle(JSONObject jsonObject) {
        this.type = jsonObject.optString("type");
        JSONArray coords = jsonObject.optJSONArray("coordinates");
        Double lat = coords.optDouble(0);
        Double lon = coords.optDouble(1);
        this.coordinates = new LatLng(lat, lon);
        this.radius = jsonObject.optString("radius");

    }

    public GeoCircle(String type, LatLng coordinates, String radius) {
        this.type = type;
        this.coordinates = coordinates;
        this.radius = radius;
    }

    public GeoCircle() {
        this.coordinates = new LatLng(0, 0);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
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

    public JSONObject toJSON() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("coordinates", coordinates.toString());
        map.put("radius", radius);
        return new JSONObject(map);
    }
}
