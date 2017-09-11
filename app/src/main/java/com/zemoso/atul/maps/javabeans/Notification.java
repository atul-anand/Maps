package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class Notification {
    private String for_user;
    private Boolean mark_as_read;
    private String message;
    private String notification_priority;
    private String notification_time;
    private String organization_id;
    private JSONObject props;
    private String aircraft_id;
    private String contract_granted_no;
    private String contract_end_time;
    private String contract_start_time;
    private String gufi;
    private String contract_id;
    private String contract_state;
    private String pilot_id;
    private String route_id;
    private String route_name;
    private String route_owner_id;
    private String route_owner_name;
    private String flight_plan_id;
    private String flight_plan_name;
    private String flight_plan_type;
    private JSONObject location;
    private String domain_id;

    public Notification(JSONObject jsonObject) {
        this.for_user = jsonObject.optString("for_user");
        this.mark_as_read = jsonObject.optBoolean("mark_as_read");
        this.message = jsonObject.optString("message");
        this.notification_priority = jsonObject.optString("notification_priority");
        this.notification_time = jsonObject.optString("notification_time");
        this.organization_id = jsonObject.optString("organization_id");
        this.props = jsonObject.optJSONObject("props");
        this.aircraft_id = jsonObject.optString("aircraft_id");
        this.contract_granted_no = jsonObject.optString("contract_granted_no");
        this.contract_end_time = jsonObject.optString("contract_end_time");
        this.contract_start_time = jsonObject.optString("contract_start_time");
        this.gufi = jsonObject.optString("gufi");
        this.contract_id = jsonObject.optString("contract_id");
        this.contract_state = jsonObject.optString("contract_state");
        this.pilot_id = jsonObject.optString("pilot_id");
        this.route_id = jsonObject.optString("route_id");
        this.route_name = jsonObject.optString("route_name");
        this.route_owner_id = jsonObject.optString("route_owner_id");
        this.route_owner_name = jsonObject.optString("route_owner_name");
        this.flight_plan_id = jsonObject.optString("flight_plan_id");
        this.flight_plan_name = jsonObject.optString("flight_plan_name");
        this.flight_plan_type = jsonObject.optString("flight_plan_type");
        this.location = jsonObject.optJSONObject("location");
        this.domain_id = jsonObject.optString("domain_id");
    }

    public String getFor_user() {
        return for_user;
    }

    public void setFor_user(String for_user) {
        this.for_user = for_user;
    }

    public Boolean getMark_as_read() {
        return mark_as_read;
    }

    public void setMark_as_read(Boolean mark_as_read) {
        this.mark_as_read = mark_as_read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotification_priority() {
        return notification_priority;
    }

    public void setNotification_priority(String notification_priority) {
        this.notification_priority = notification_priority;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public JSONObject getProps() {
        return props;
    }

    public void setProps(JSONObject props) {
        this.props = props;
    }

    public String getAircraft_id() {
        return aircraft_id;
    }

    public void setAircraft_id(String aircraft_id) {
        this.aircraft_id = aircraft_id;
    }

    public String getContract_granted_no() {
        return contract_granted_no;
    }

    public void setContract_granted_no(String contract_granted_no) {
        this.contract_granted_no = contract_granted_no;
    }

    public String getContract_end_time() {
        return contract_end_time;
    }

    public void setContract_end_time(String contract_end_time) {
        this.contract_end_time = contract_end_time;
    }

    public String getContract_start_time() {
        return contract_start_time;
    }

    public void setContract_start_time(String contract_start_time) {
        this.contract_start_time = contract_start_time;
    }

    public String getGufi() {
        return gufi;
    }

    public void setGufi(String gufi) {
        this.gufi = gufi;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getContract_state() {
        return contract_state;
    }

    public void setContract_state(String contract_state) {
        this.contract_state = contract_state;
    }

    public String getPilot_id() {
        return pilot_id;
    }

    public void setPilot_id(String pilot_id) {
        this.pilot_id = pilot_id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getRoute_owner_id() {
        return route_owner_id;
    }

    public void setRoute_owner_id(String route_owner_id) {
        this.route_owner_id = route_owner_id;
    }

    public String getRoute_owner_name() {
        return route_owner_name;
    }

    public void setRoute_owner_name(String route_owner_name) {
        this.route_owner_name = route_owner_name;
    }

    public String getFlight_plan_id() {
        return flight_plan_id;
    }

    public void setFlight_plan_id(String flight_plan_id) {
        this.flight_plan_id = flight_plan_id;
    }

    public String getFlight_plan_name() {
        return flight_plan_name;
    }

    public void setFlight_plan_name(String flight_plan_name) {
        this.flight_plan_name = flight_plan_name;
    }

    public String getFlight_plan_type() {
        return flight_plan_type;
    }

    public void setFlight_plan_type(String flight_plan_type) {
        this.flight_plan_type = flight_plan_type;
    }

    public JSONObject getLocation() {
        return location;
    }

    public void setLocation(JSONObject location) {
        this.location = location;
    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"for_user\":\"" + for_user + '\"' +
                ", \"mark_as_read\":" + mark_as_read +
                ", \"message\":\"" + message + '\"' +
                ", \"notification_priority\":\"" + notification_priority + '\"' +
                ", \"notification_time\":\"" + notification_time + '\"' +
                ", \"organization_id\":\"" + organization_id + '\"' +
                ", \"props\":" + props +
                ", \"aircraft_id\":\"" + aircraft_id + '\"' +
                ", \"contract_granted_no\":\"" + contract_granted_no + '\"' +
                ", \"contract_end_time\":\"" + contract_end_time + '\"' +
                ", \"contract_start_time\":\"" + contract_start_time + '\"' +
                ", \"gufi\":\"" + gufi + '\"' +
                ", \"contract_id\":\"" + contract_id + '\"' +
                ", \"contract_state\":\"" + contract_state + '\"' +
                ", \"pilot_id\":\"" + pilot_id + '\"' +
                ", \"route_id\":\"" + route_id + '\"' +
                ", \"route_name\":\"" + route_name + '\"' +
                ", \"route_owner_id\":\"" + route_owner_id + '\"' +
                ", \"route_owner_name\":\"" + route_owner_name + '\"' +
                ", \"flight_plan_id\":\"" + flight_plan_id + '\"' +
                ", \"flight_plan_name\":\"" + flight_plan_name + '\"' +
                ", \"flight_plan_type\":\"" + flight_plan_type + '\"' +
                ", \"location\":" + location +
                ", \"domain_id\":\"" + domain_id + '\"' +
                '}';
    }
}
