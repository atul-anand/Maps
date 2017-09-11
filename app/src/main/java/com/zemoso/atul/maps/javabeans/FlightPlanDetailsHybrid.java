package com.zemoso.atul.maps.javabeans;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zemoso on 8/9/17.
 */

public class FlightPlanDetailsHybrid {
    private List<ReservedVolume> reserved_volumes;
    private List<Waypoint> waypoints_info;
    private String flight_plan_type;
    private String flight_plan_category;
    private String route_id;
    private String altitude_mode;
    private JSONObject props;
    private Double gross_weight_lb;
    private Double fuel_weight_lb;
    private Double fuel_indicator;
    private Double payload_weight_lb;

    public FlightPlanDetailsHybrid() {
    }

    public FlightPlanDetailsHybrid(JSONObject jsonObject) {
        reserved_volumes = new ArrayList<>();
        JSONArray reservedVolumes = jsonObject.optJSONArray("reserved_volumes");
        for (int i = 0; i < reservedVolumes.length(); i++) {
            ReservedVolume reservedVolume = new ReservedVolume(reservedVolumes.optJSONObject(i));
            reserved_volumes.add(reservedVolume);
        }
        waypoints_info = new ArrayList<>();
        JSONArray waypointsInfo = jsonObject.optJSONArray("waypoints_info");
        for (int i = 0; i < waypointsInfo.length(); i++) {
            Waypoint waypoint = new Waypoint(waypointsInfo.optJSONObject(i));
            waypoints_info.add(waypoint);
        }
        flight_plan_type = jsonObject.optString("flight_plan_type");
        flight_plan_category = jsonObject.optString("flight_plan_category");
        route_id = jsonObject.optString("route_id");
        altitude_mode = jsonObject.optString("altitude_mode");
        props = jsonObject.optJSONObject("props");
        gross_weight_lb = jsonObject.optDouble("gross_weight_lb");
        fuel_weight_lb = jsonObject.optDouble("fuel_weight_lb");
        fuel_indicator = jsonObject.optDouble("fuel_indicator");
        payload_weight_lb = jsonObject.optDouble("payload_weight_lb");
    }

    public List<ReservedVolume> getReserved_volumes() {
        return reserved_volumes;
    }

    public void setReserved_volumes(List<ReservedVolume> reserved_volumes) {
        this.reserved_volumes = reserved_volumes;
    }

    public List<Waypoint> getWaypoints_info() {
        return waypoints_info;
    }

    public void setWaypoints_info(List<Waypoint> waypoints_info) {
        this.waypoints_info = waypoints_info;
    }

    public String getFlight_plan_type() {
        return flight_plan_type;
    }

    public void setFlight_plan_type(String flight_plan_type) {
        this.flight_plan_type = flight_plan_type;
    }

    public String getFlight_plan_category() {
        return flight_plan_category;
    }

    public void setFlight_plan_category(String flight_plan_category) {
        this.flight_plan_category = flight_plan_category;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getAltitude_mode() {
        return altitude_mode;
    }

    public void setAltitude_mode(String altitude_mode) {
        this.altitude_mode = altitude_mode;
    }

    public JSONObject getProps() {
        return props;
    }

    public void setProps(JSONObject props) {
        this.props = props;
    }

    public Double getGross_weight_lb() {
        return gross_weight_lb;
    }

    public void setGross_weight_lb(Double gross_weight_lb) {
        this.gross_weight_lb = gross_weight_lb;
    }

    public Double getFuel_weight_lb() {
        return fuel_weight_lb;
    }

    public void setFuel_weight_lb(Double fuel_weight_lb) {
        this.fuel_weight_lb = fuel_weight_lb;
    }

    public Double getFuel_indicator() {
        return fuel_indicator;
    }

    public void setFuel_indicator(Double fuel_indicator) {
        this.fuel_indicator = fuel_indicator;
    }

    public Double getPayload_weight_lb() {
        return payload_weight_lb;
    }

    public void setPayload_weight_lb(Double payload_weight_lb) {
        this.payload_weight_lb = payload_weight_lb;
    }

    @Override

    public String toString() {
        return "{" +
                "\"reserved_volumes\":" + reserved_volumes +
                ", \"waypoints_info\":" + waypoints_info +
                ", \"flight_plan_type\":\"" + flight_plan_type + '\"' +
                ", \"flight_plan_category\":\"" + flight_plan_category + '\"' +
                ", \"route_id\":\"" + route_id + '\"' +
                ", \"altitude_mode\":\"" + altitude_mode + '\"' +
                ", \"props\":" + props +
                ", \"gross_weight_lb\":" + gross_weight_lb +
                ", \"fuel_weight_lb\":" + fuel_weight_lb +
                ", \"fuel_indicator\":" + fuel_indicator +
                ", \"payload_weight_lb\":" + payload_weight_lb +
                '}';
    }
}
