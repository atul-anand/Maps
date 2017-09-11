package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class Aircraft {
    private String name;
    private String aircraft_model_id;
    private String organization_id;
    private String pilot_id;
    private String id;
    private String serial_number;
    private String created_at;
    private String created_by;
    private String modified_at;
    private String modified_by;
    private Boolean is_deleted;
    private String retailer_name;
    private Integer flight_hours;
    private JSONObject rnp_accuracy;
    private JSONObject sensor_package;
    private JSONObject aircraft_details;

    public Aircraft(JSONObject jsonObject) {
        this.name = jsonObject.optString("name");
        this.aircraft_model_id = jsonObject.optString("aircraft_model_id");
        this.organization_id = jsonObject.optString("organization_id");
        this.pilot_id = jsonObject.optString("pilot_id");
        this.id = jsonObject.optString("id");
        this.serial_number = jsonObject.optString("serial_number");
        this.created_at = jsonObject.optString("created_at");
        this.created_by = jsonObject.optString("created_by");
        this.is_deleted = jsonObject.optBoolean("is_deleted");
        this.modified_at = jsonObject.optString("modified_at");
        this.modified_by = jsonObject.optString("modified_by");
        this.retailer_name = jsonObject.optString("retailer_name");
        this.flight_hours = jsonObject.optInt("flight_hours");
        this.rnp_accuracy = jsonObject.optJSONObject("rnp_accuracy");
        this.sensor_package = jsonObject.optJSONObject("sensor_package");
        this.aircraft_details = jsonObject.optJSONObject("aircraft_details");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAircraft_model_id() {
        return aircraft_model_id;
    }

    public void setAircraft_model_id(String aircraft_model_id) {
        this.aircraft_model_id = aircraft_model_id;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getPilot_id() {
        return pilot_id;
    }

    public void setPilot_id(String pilot_id) {
        this.pilot_id = pilot_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getModified_at() {
        return modified_at;
    }

    public void setModified_at(String modified_at) {
        this.modified_at = modified_at;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getRetailer_name() {
        return retailer_name;
    }

    public void setRetailer_name(String retailer_name) {
        this.retailer_name = retailer_name;
    }

    public Integer getFlight_hours() {
        return flight_hours;
    }

    public void setFlight_hours(Integer flight_hours) {
        this.flight_hours = flight_hours;
    }

    public JSONObject getRnp_accuracy() {
        return rnp_accuracy;
    }

    public void setRnp_accuracy(JSONObject rnp_accuracy) {
        this.rnp_accuracy = rnp_accuracy;
    }

    public JSONObject getSensor_package() {
        return sensor_package;
    }

    public void setSensor_package(JSONObject sensor_package) {
        this.sensor_package = sensor_package;
    }

    public JSONObject getAircraft_details() {
        return aircraft_details;
    }

    public void setAircraft_details(JSONObject aircraft_details) {
        this.aircraft_details = aircraft_details;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"aircraft_model_id\":\"" + aircraft_model_id + '\"' +
                ", \"organization_id\":\"" + organization_id + '\"' +
                ", \"pilot_id\":\"" + pilot_id + '\"' +
                ", \"id\":\"" + id + '\"' +
                ", \"serial_number\":\"" + serial_number + '\"' +
                ", \"created_at\":\"" + created_at + '\"' +
                ", \"created_by\":\"" + created_by + '\"' +
                ", \"modified_at\":\"" + modified_at + '\"' +
                ", \"modified_by\":\"" + modified_by + '\"' +
                ", \"is_deleted\":" + is_deleted +
                ", \"retailer_name\":\"" + retailer_name + '\"' +
                ", \"flight_hours\":" + flight_hours +
                ", \"rnp_accuracy\":" + rnp_accuracy +
                ", \"sensor_package\":" + sensor_package +
                ", \"aircraft_details\":" + aircraft_details +
                '}';
    }
}
