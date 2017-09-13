package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class AirspaceDetails {
    private String source;
    private String availability;
    private String description;
    private String status;
    private String external_id;
    private Integer altitude_ft;
    private Integer height_ft;
    private AirspaceGeometry airspace_geometry;
    private String start_time;
    private String end_time;
    private String airspace_category;
    private String airspace_type;
    private Integer protected_geography;
    private String altitude_mode;

    public AirspaceDetails(JSONObject jsonObject) {
        this.source = jsonObject.optString("source");
        this.availability = jsonObject.optString("availability");
        this.description = jsonObject.optString("description");
        this.status = jsonObject.optString("status");
        this.external_id = jsonObject.optString("external_id");
        this.altitude_ft = jsonObject.optInt("altitude_ft");
        this.height_ft = jsonObject.optInt("height_ft");
        this.airspace_geometry = new AirspaceGeometry(jsonObject.optJSONObject("airspace_geometry"));
        this.start_time = jsonObject.optString("start_time");
        this.end_time = jsonObject.optString("end_time");
        this.airspace_category = jsonObject.optString("airspace_category");
        this.airspace_type = jsonObject.optString("airspace_type");
        this.protected_geography = jsonObject.optInt("protected_geography");
        this.altitude_mode = jsonObject.optString("altitude_mode");
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public Integer getAltitude_ft() {
        return altitude_ft;
    }

    public void setAltitude_ft(Integer altitude_ft) {
        this.altitude_ft = altitude_ft;
    }

    public Integer getHeight_ft() {
        return height_ft;
    }

    public void setHeight_ft(Integer height_ft) {
        this.height_ft = height_ft;
    }

    public AirspaceGeometry getAirspace_geometry() {
        return airspace_geometry;
    }

    public void setAirspace_geometry(AirspaceGeometry airspace_geometry) {
        this.airspace_geometry = airspace_geometry;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAirspace_category() {
        return airspace_category;
    }

    public void setAirspace_category(String airspace_category) {
        this.airspace_category = airspace_category;
    }

    public String getAirspace_type() {
        return airspace_type;
    }

    public void setAirspace_type(String airspace_type) {
        this.airspace_type = airspace_type;
    }

    public Integer getProtected_geography() {
        return protected_geography;
    }

    public void setProtected_geography(Integer protected_geography) {
        this.protected_geography = protected_geography;
    }

    public String getAltitude_mode() {
        return altitude_mode;
    }

    public void setAltitude_mode(String altitude_mode) {
        this.altitude_mode = altitude_mode;
    }

    @Override
    public String toString() {
        return "{" +
                "\"source\":\"" + source + '\"' +
                ", \"availability\":\"" + availability + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"status\":\"" + status + '\"' +
                ", \"external_id\":\"" + external_id + '\"' +
                ", \"altitude_ft\":" + altitude_ft +
                ", \"height_ft\":" + height_ft +
                ", \"airspace_geometry\":" + airspace_geometry +
                ", \"start_time\":\"" + start_time + '\"' +
                ", \"end_time\":\"" + end_time + '\"' +
                ", \"airspace_category\":\"" + airspace_category + '\"' +
                ", \"airspace_type\":\"" + airspace_type + '\"' +
                ", \"protected_geography\":" + protected_geography +
                ", \"altitude_mode\":\"" + altitude_mode + '\"' +
                '}';
    }
}
