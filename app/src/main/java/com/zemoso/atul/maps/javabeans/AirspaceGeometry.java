package com.zemoso.atul.maps.javabeans;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zemoso on 11/9/17.
 */

public class AirspaceGeometry {
    private String type;
    private List<Point2D> coordinates;

    public AirspaceGeometry(JSONObject jsonObject) {
        this.type = jsonObject.optString("type");
        JSONArray jsonArray = jsonObject.optJSONArray("coordinates");
        coordinates = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++)
            coordinates.add(new Point2D(jsonArray.optJSONArray(i)));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Point2D> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Point2D> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + type + '\"' +
                ", \"coordinates\":" + coordinates +
                '}';
    }
}
