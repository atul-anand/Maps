package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 8/9/17.
 */

public class Waypoint {
    private Integer min_altitude_ft;
    private Integer max_altitude_ft;
    private GeoPoint wp_location;
    private String effective_time_begin;
    private String effective_time_end;
    private Integer min_speed_kn;
    private Integer max_speed_kn;
    private Integer change_yaw;
    private String mission_notes;
    private String turn_type;
    private Boolean segment_end;
    private JSONObject wp_props;

    public Waypoint() {
    }

    public Waypoint(JSONObject jsonObject) {
        this.min_altitude_ft = jsonObject.optInt("min_altitude_ft");
        this.max_altitude_ft = jsonObject.optInt("max_altitude_ft");
        this.wp_location = new GeoPoint(jsonObject.optJSONObject("wp_location"));
        this.effective_time_begin = jsonObject.optString("effective_time_begin");
        this.effective_time_end = jsonObject.optString("effective_time_end");
        this.min_speed_kn = jsonObject.optInt("min_speed_kn");
        this.max_speed_kn = jsonObject.optInt("max_speed_kn");
        this.change_yaw = jsonObject.optInt("change_yaw");
        this.mission_notes = jsonObject.optString("mission_notes");
        this.turn_type = jsonObject.optString("turn_type");
        this.segment_end = jsonObject.optBoolean("segment_end");
        this.wp_props = jsonObject.optJSONObject("wp_props");
    }

    public Integer getMin_altitude_ft() {
        return min_altitude_ft;
    }

    public void setMin_altitude_ft(Integer min_altitude_ft) {
        this.min_altitude_ft = min_altitude_ft;
    }

    public Integer getMax_altitude_ft() {
        return max_altitude_ft;
    }

    public void setMax_altitude_ft(Integer max_altitude_ft) {
        this.max_altitude_ft = max_altitude_ft;
    }

    public GeoPoint getWp_location() {
        return wp_location;
    }

    public void setWp_location(GeoPoint wp_location) {
        this.wp_location = wp_location;
    }

    public String getEffective_time_begin() {
        return effective_time_begin;
    }

    public void setEffective_time_begin(String effective_time_begin) {
        this.effective_time_begin = effective_time_begin;
    }

    public String getEffective_time_end() {
        return effective_time_end;
    }

    public void setEffective_time_end(String effective_time_end) {
        this.effective_time_end = effective_time_end;
    }

    public Integer getMin_speed_kn() {
        return min_speed_kn;
    }

    public void setMin_speed_kn(Integer min_speed_kn) {
        this.min_speed_kn = min_speed_kn;
    }

    public Integer getMax_speed_kn() {
        return max_speed_kn;
    }

    public void setMax_speed_kn(Integer max_speed_kn) {
        this.max_speed_kn = max_speed_kn;
    }

    public Integer getChange_yaw() {
        return change_yaw;
    }

    public void setChange_yaw(Integer change_yaw) {
        this.change_yaw = change_yaw;
    }

    public String getMission_notes() {
        return mission_notes;
    }

    public void setMission_notes(String mission_notes) {
        this.mission_notes = mission_notes;
    }

    public String getTurn_type() {
        return turn_type;
    }

    public void setTurn_type(String turn_type) {
        this.turn_type = turn_type;
    }

    public Boolean getSegment_end() {
        return segment_end;
    }

    public void setSegment_end(Boolean segment_end) {
        this.segment_end = segment_end;
    }

    public JSONObject getWp_props() {
        return wp_props;
    }

    public void setWp_props(JSONObject wp_props) {
        this.wp_props = wp_props;
    }

    @Override
    public String toString() {
        return "{" +
                "\"min_altitude_ft\":" + min_altitude_ft +
                ", \"max_altitude_ft\":" + max_altitude_ft +
                ", \"wp_location\":" + wp_location +
                ", \"effective_time_begin\":\"" + effective_time_begin + '\"' +
                ", \"effective_time_end\":\"" + effective_time_end + '\"' +
                ", \"min_speed_kn\":" + min_speed_kn +
                ", \"max_speed_kn\":" + max_speed_kn +
                ", \"change_yaw\":" + change_yaw +
                ", \"mission_notes\":\"" + mission_notes + '\"' +
                ", \"turn_type\":\"" + turn_type + '\"' +
                ", \"segment_end\":" + segment_end +
                ", \"wp_props\":" + wp_props +
                '}';
    }
}
