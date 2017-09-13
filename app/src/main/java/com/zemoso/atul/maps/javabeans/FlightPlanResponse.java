package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 8/9/17.
 */

public class FlightPlanResponse {

    private String id;
    private String name;
    private String pilot_id;
    private String created_at;
    private String created_by;
    private String aircraft_id;
    private String status;
    private String organization_id;
    private String description;
    private String contract_id;
    private FlightPlanDetailsHybrid flight_plan_details;

    public FlightPlanResponse() {
    }

    public FlightPlanResponse(JSONObject jsonObject) {
        id = jsonObject.optString("id");
        name = jsonObject.optString("name");
        pilot_id = jsonObject.optString("pilot_id");
        created_at = jsonObject.optString("created_at");
        created_by = jsonObject.optString("created_by");
        aircraft_id = jsonObject.optString("aircraft_id");
        status = jsonObject.optString("status");
        organization_id = jsonObject.optString("organization_id");
        description = jsonObject.optString("description");
        contract_id = jsonObject.optString("contract_id");
        flight_plan_details = new FlightPlanDetailsHybrid(jsonObject.optJSONObject("flight_plan_details"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
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
                "\"id\":\"" + id + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"pilot_id\":\"" + pilot_id + '\"' +
                ", \"created_at\":\"" + created_at + '\"' +
                ", \"created_by\":\"" + created_by + '\"' +
                ", \"aircraft_id\":\"" + aircraft_id + '\"' +
                ", \"status\":\"" + status + '\"' +
                ", \"organization_id\":\"" + organization_id + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"contract_id\":\"" + contract_id + '\"' +
                ", \"flight_plan_details\":" + flight_plan_details +
                '}';
    }
}
