package com.zemoso.atul.maps.javabeans;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zemoso on 11/9/17.
 */

public class GeoPolygon {
    private List<Point2D> coordinates;
    private String type;

    public GeoPolygon(JSONObject jsonObject) {
        coordinates = new ArrayList<>();
        JSONArray jsonArray = jsonObject.optJSONArray("coordinates");
        for (int i = 0; i < jsonArray.length(); i++)
            coordinates.add(new Point2D(jsonArray.optJSONArray(i)));
        type = jsonObject.optString("type");
    }

    public List<Point2D> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Point2D> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "\"coordinates\":" + coordinates +
                ", \"type\":\"" + type + '\"' +
                '}';
    }
}
