package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class ContractStateHistory {
    private String contract_state;
    private String start_time;
    private String end_time;
    private String reason_for_change;
    private String previous_contract_state;

    public ContractStateHistory(JSONObject jsonObject) {
        this.contract_state = jsonObject.optString("contract_state");
        this.start_time = jsonObject.optString("start_time");
        this.end_time = jsonObject.optString("end_time");
        this.reason_for_change = jsonObject.optString("reason_for_change");
        this.previous_contract_state = jsonObject.optString("previous_contract_state");
    }

    public String getContract_state() {
        return contract_state;
    }

    public void setContract_state(String contract_state) {
        this.contract_state = contract_state;
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

    public String getReason_for_change() {
        return reason_for_change;
    }

    public void setReason_for_change(String reason_for_change) {
        this.reason_for_change = reason_for_change;
    }

    public String getPrevious_contract_state() {
        return previous_contract_state;
    }

    public void setPrevious_contract_state(String previous_contract_state) {
        this.previous_contract_state = previous_contract_state;
    }

    @Override
    public String toString() {
        return "{" +
                "\"contract_state\":\"" + contract_state + '\"' +
                ", \"start_time\":\"" + start_time + '\"' +
                ", \"end_time\":\"" + end_time + '\"' +
                ", \"reason_for_change\":\"" + reason_for_change + '\"' +
                ", \"previous_contract_state\":\"" + previous_contract_state + '\"' +
                '}';
    }
}
