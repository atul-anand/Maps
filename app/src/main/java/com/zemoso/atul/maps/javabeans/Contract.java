package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class Contract {
    private String name;
    private String gufi;
    private String flight_plan_id;
    private String organization_id;
    private String pilot_id;
    private String start_time;
    private String end_time;
    private String created_at;
    private String created_by;
    private Boolean is_deleted;
    private String modified_at;
    private String modified_by;
    private ContractDetails contract_details;

    public Contract(JSONObject jsonObject) {
        this.name = jsonObject.optString("name");
        this.gufi = jsonObject.optString("gufi");
        this.flight_plan_id = jsonObject.optString("flight_plan_id");
        this.organization_id = jsonObject.optString("organization_id");
        this.pilot_id = jsonObject.optString("pilot_id");
        this.start_time = jsonObject.optString("start_time");
        this.end_time = jsonObject.optString("end_time");
        this.created_at = jsonObject.optString("created_at");
        this.created_by = jsonObject.optString("created_by");
        this.is_deleted = jsonObject.optBoolean("is_deleted");
        this.modified_at = jsonObject.optString("modified_at");
        this.modified_by = jsonObject.optString("modified_by");
        this.contract_details = new ContractDetails(jsonObject.optJSONObject("contract_details"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGufi() {
        return gufi;
    }

    public void setGufi(String gufi) {
        this.gufi = gufi;
    }

    public String getFlight_plan_id() {
        return flight_plan_id;
    }

    public void setFlight_plan_id(String flight_plan_id) {
        this.flight_plan_id = flight_plan_id;
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

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
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

    public ContractDetails getContract_details() {
        return contract_details;
    }

    public void setContract_details(ContractDetails contract_details) {
        this.contract_details = contract_details;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"gufi\":\"" + gufi + '\"' +
                ", \"flight_plan_id\":\"" + flight_plan_id + '\"' +
                ", \"organization_id\":\"" + organization_id + '\"' +
                ", \"pilot_id\":\"" + pilot_id + '\"' +
                ", \"start_time\":\"" + start_time + '\"' +
                ", \"end_time\":\"" + end_time + '\"' +
                ", \"created_at\":\"" + created_at + '\"' +
                ", \"created_by\":\"" + created_by + '\"' +
                ", \"is_deleted\":" + is_deleted +
                ", \"modified_at\":\"" + modified_at + '\"' +
                ", \"modified_by\":\"" + modified_by + '\"' +
                ", \"contract_details\":" + contract_details +
                '}';
    }
}
