package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class AircraftModel {
    private String id;
    private String manufacturer;
    private String model_type;
    private String model_code;
    private String icao_adsb_code;
    private Boolean lnav_capability;
    private Boolean vnav_capability;
    private JSONObject rnp_accuracy;
    private String created_at;
    private String created_by;
    private String modified_at;
    private String modified_by;
    private Boolean is_deleted;
    private JSONObject sensor_package;
    private JSONObject performance_model;
    private JSONObject aircraft_model_details;

    public AircraftModel(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.manufacturer = jsonObject.optString("manufacturer");
        this.model_type = jsonObject.optString("model_type");
        this.model_code = jsonObject.optString("model_code");
        this.icao_adsb_code = jsonObject.optString("icao_adsb_code");
        this.lnav_capability = jsonObject.optBoolean("lnav_capability");
        this.vnav_capability = jsonObject.optBoolean("vnav_capability");
        this.rnp_accuracy = jsonObject.optJSONObject("rnp_accuracy");
        this.created_at = jsonObject.optString("created_at");
        this.created_by = jsonObject.optString("created_by");
        this.modified_at = jsonObject.optString("modified_at");
        this.modified_by = jsonObject.optString("modified_by");
        this.is_deleted = jsonObject.optBoolean("is_deleted");
        this.sensor_package = jsonObject.optJSONObject("sensor_package");
        this.performance_model = jsonObject.optJSONObject("performance_model");
        this.aircraft_model_details = jsonObject.optJSONObject("aircraft_model_details");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    public String getModel_code() {
        return model_code;
    }

    public void setModel_code(String model_code) {
        this.model_code = model_code;
    }

    public String getIcao_adsb_code() {
        return icao_adsb_code;
    }

    public void setIcao_adsb_code(String icao_adsb_code) {
        this.icao_adsb_code = icao_adsb_code;
    }

    public Boolean getLnav_capability() {
        return lnav_capability;
    }

    public void setLnav_capability(Boolean lnav_capability) {
        this.lnav_capability = lnav_capability;
    }

    public Boolean getVnav_capability() {
        return vnav_capability;
    }

    public void setVnav_capability(Boolean vnav_capability) {
        this.vnav_capability = vnav_capability;
    }

    public JSONObject getRnp_accuracy() {
        return rnp_accuracy;
    }

    public void setRnp_accuracy(JSONObject rnp_accuracy) {
        this.rnp_accuracy = rnp_accuracy;
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

    public JSONObject getSensor_package() {
        return sensor_package;
    }

    public void setSensor_package(JSONObject sensor_package) {
        this.sensor_package = sensor_package;
    }

    public JSONObject getPerformance_model() {
        return performance_model;
    }

    public void setPerformance_model(JSONObject performance_model) {
        this.performance_model = performance_model;
    }

    public JSONObject getAircraft_model_details() {
        return aircraft_model_details;
    }

    public void setAircraft_model_details(JSONObject aircraft_model_details) {
        this.aircraft_model_details = aircraft_model_details;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"manufacturer\":\"" + manufacturer + '\"' +
                ", \"model_type\":\"" + model_type + '\"' +
                ", \"model_code\":\"" + model_code + '\"' +
                ", \"icao_adsb_code\":\"" + icao_adsb_code + '\"' +
                ", \"lnav_capability\":" + lnav_capability +
                ", \"vnav_capability\":" + vnav_capability +
                ", \"rnp_accuracy\":" + rnp_accuracy +
                ", \"created_at\":\"" + created_at + '\"' +
                ", \"created_by\":\"" + created_by + '\"' +
                ", \"modified_at\":\"" + modified_at + '\"' +
                ", \"modified_by\":\"" + modified_by + '\"' +
                ", \"is_deleted\":\"" + is_deleted + '\"' +
                ", \"sensor_package\":" + sensor_package +
                ", \"performance_model\":" + performance_model +
                ", \"aircraft_model_details\":" + aircraft_model_details +
                '}';
    }
}
