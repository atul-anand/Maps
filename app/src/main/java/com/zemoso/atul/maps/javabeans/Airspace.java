package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class Airspace {
    private String id;
    private String name;
    private String domain_id;
    private String created_at;
    private String created_by;
    private String modified_at;
    private String modified_by;
    private Boolean is_deleted;
    private AirspaceDetails airspace_details;

    public Airspace(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.name = jsonObject.optString("name");
        this.domain_id = jsonObject.optString("domain_id");
        this.created_at = jsonObject.optString("created_at");
        this.created_by = jsonObject.optString("created_by");
        this.modified_at = jsonObject.optString("modified_at");
        this.modified_by = jsonObject.optString("modified_by");
        this.is_deleted = jsonObject.optBoolean("is_deleted");
        this.airspace_details = new AirspaceDetails(jsonObject.optJSONObject("airspace_details"));
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

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
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

    public AirspaceDetails getAirspace_details() {
        return airspace_details;
    }

    public void setAirspace_details(AirspaceDetails airspace_details) {
        this.airspace_details = airspace_details;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"domain_id\":\"" + domain_id + '\"' +
                ", \"created_at\":\"" + created_at + '\"' +
                ", \"created_by\":\"" + created_by + '\"' +
                ", \"modified_at\":\"" + modified_at + '\"' +
                ", \"modified_by\":\"" + modified_by + '\"' +
                ", \"is_deleted\":" + is_deleted +
                ", \"airspace_details\":" + airspace_details +
                '}';
    }
}
