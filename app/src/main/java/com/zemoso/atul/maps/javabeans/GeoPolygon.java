package com.zemoso.atul.maps.javabeans;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zemoso on 11/9/17.
 */

public class GeoPolygon {
    private List<LatLng> coordinates;
    private String type;

    public GeoPolygon(JSONObject jsonObject) {
        coordinates = new ArrayList<>();
        JSONArray coords = jsonObject.optJSONArray("coordinates");
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONArray coords = jsonArray.optJSONArray(i);
        Double lat = coords.optDouble(0);
        Double lon = coords.optDouble(1);
        coordinates.add(new LatLng(lat, lon));
//        }
        type = jsonObject.optString("type");
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<LatLng> coordinates) {
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
