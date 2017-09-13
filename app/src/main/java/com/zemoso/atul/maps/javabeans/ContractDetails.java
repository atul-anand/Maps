package com.zemoso.atul.maps.javabeans;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zemoso on 11/9/17.
 */

public class ContractDetails {
    private JSONObject props;
    private LatLng pilot_location;
    private String description;
    private String contract_state;
    private List<ContractStateHistory> contract_state_history;
    private String decision_time;
    private Contact primary_contact;
    private Contact secondary_contact;
    private List<OperationalVolume> operation_volumes_info;

    public ContractDetails(JSONObject jsonObject) {
        this.props = jsonObject.optJSONObject("props");
        JSONObject coords = jsonObject.optJSONObject("pilot_location");
        Double lat = coords.optDouble("lat");
        Double lon = coords.optDouble("lon");
        this.pilot_location = new LatLng(lat, lon);
        this.description = jsonObject.optString("description");
        this.contract_state = jsonObject.optString("contract_state");
        contract_state_history = new ArrayList<>();
        JSONArray contractStateHistories = jsonObject.optJSONArray("contract_state_history");
        for (int i = 0; i < contractStateHistories.length(); i++) {
            ContractStateHistory contractStateHistory = new ContractStateHistory(contractStateHistories.optJSONObject(i));
            contract_state_history.add(contractStateHistory);
        }
        this.decision_time = jsonObject.optString("decision_time");
        this.primary_contact = new Contact(jsonObject.optJSONObject("primary_contact"));
        this.secondary_contact = new Contact(jsonObject.optJSONObject("secondary_contact"));
        operation_volumes_info = new ArrayList<>();
        JSONArray volumes = jsonObject.optJSONArray("operation_volumes_info");
        for (int i = 0; i < volumes.length(); i++) {
            OperationalVolume operationalVolume = new OperationalVolume(volumes.optJSONObject(i));
            operation_volumes_info.add(operationalVolume);
        }
    }

    public JSONObject getProps() {
        return props;
    }

    public void setProps(JSONObject props) {
        this.props = props;
    }

    public LatLng getPilot_location() {
        return pilot_location;
    }

    public void setPilot_location(LatLng pilot_location) {
        this.pilot_location = pilot_location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContract_state() {
        return contract_state;
    }

    public void setContract_state(String contract_state) {
        this.contract_state = contract_state;
    }

    public List<ContractStateHistory> getContract_state_history() {
        return contract_state_history;
    }

    public void setContract_state_history(List<ContractStateHistory> contract_state_history) {
        this.contract_state_history = contract_state_history;
    }

    public String getDecision_time() {
        return decision_time;
    }

    public void setDecision_time(String decision_time) {
        this.decision_time = decision_time;
    }

    public Contact getPrimary_contact() {
        return primary_contact;
    }

    public void setPrimary_contact(Contact primary_contact) {
        this.primary_contact = primary_contact;
    }

    public Contact getSecondary_contact() {
        return secondary_contact;
    }

    public void setSecondary_contact(Contact secondary_contact) {
        this.secondary_contact = secondary_contact;
    }

    public List<OperationalVolume> getOperation_volumes_info() {
        return operation_volumes_info;
    }

    public void setOperation_volumes_info(List<OperationalVolume> operation_volumes_info) {
        this.operation_volumes_info = operation_volumes_info;
    }

    @Override
    public String toString() {
        return "{" +
                "\"props\":" + props +
                ", \"pilot_location\":" + pilot_location +
                ", \"description\":\"" + description + '\"' +
                ", \"contract_state\":\"" + contract_state + '\"' +
                ", \"contract_state_history\":" + contract_state_history +
                ", \"decision_time\":\"" + decision_time + '\"' +
                ", \"primary_contact\":" + primary_contact +
                ", \"secondary_contact\":" + secondary_contact +
                ", \"operation_volumes_info\":" + operation_volumes_info +
                '}';
    }
}
