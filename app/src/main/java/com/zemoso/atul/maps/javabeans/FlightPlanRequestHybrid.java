package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 8/9/17.
 */

public class FlightPlanRequestHybrid {
    private String name;
    private String pilot_id;
    private String aircraft_id;
    private String status;
    private String organization_id;
    private String description;
    private FlightPlanDetailsHybrid flight_plan_details;

    public FlightPlanRequestHybrid() {
    }

    public FlightPlanRequestHybrid(JSONObject jsonObject) {
        name = jsonObject.optString("name");
        pilot_id = jsonObject.optString("pilot_id");
        aircraft_id = jsonObject.optString("aircraft_id");
        status = jsonObject.optString("status");
        organization_id = jsonObject.optString("organization_id");
        description = jsonObject.optString("description");
        flight_plan_details = new FlightPlanDetailsHybrid(jsonObject.optJSONObject("flight_plan_details"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPilot_id() {
        return pilot_id;
    }

    public void setPilot_id(String pilot_id) {
        this.pilot_id = pilot_id;
    }

    public String getAircraft_id() {
        return aircraft_id;
    }

    public void setAircraft_id(String aircraft_id) {
        this.aircraft_id = aircraft_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FlightPlanDetailsHybrid getFlight_plan_details() {
        return flight_plan_details;
    }

    public void setFlight_plan_details(FlightPlanDetailsHybrid flight_plan_details) {
        this.flight_plan_details = flight_plan_details;
    }

    @Override

    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"pilot_id\":\"" + pilot_id + '\"' +
                ", \"aircraft_id\":\"" + aircraft_id + '\"' +
                ", \"status\":\"" + status + '\"' +
                ", \"organization_id\":\"" + organization_id + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"flight_plan_details\":" + flight_plan_details +
                '}';
    }
}
