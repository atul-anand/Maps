package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 8/9/17.
 */

public class ReservedVolume {
    private GeoCircle reserved_volume_projection;
    private String mission_notes;
    private String effective_time_begin;
    private String effective_time_end;
    private Double min_altitude_ft;
    private Double max_altitude_ft;
    private JSONObject res_vol_props;

    public ReservedVolume() {

    }

    public ReservedVolume(JSONObject jsonObject) {
        this.reserved_volume_projection = new GeoCircle(jsonObject.optJSONObject("reserved_volume_projection"));
        this.mission_notes = jsonObject.optString("mission_notes");
        this.effective_time_begin = jsonObject.optString("effective_time_begin");
        this.effective_time_end = jsonObject.optString("effective_time_end");
        this.min_altitude_ft = jsonObject.optDouble("min_altitude_ft");
        this.max_altitude_ft = jsonObject.optDouble("max_altitude_ft");
        this.res_vol_props = jsonObject.optJSONObject("res_vol_props");
    }

    public GeoCircle getReserved_volume_projection() {
        return reserved_volume_projection;
    }

    public void setReserved_volume_projection(GeoCircle reserved_volume_projection) {
        this.reserved_volume_projection = reserved_volume_projection;
    }

    public String getMission_notes() {
        return mission_notes;
    }

    public void setMission_notes(String mission_notes) {
        this.mission_notes = mission_notes;
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

    public Double getMin_altitude_ft() {
        return min_altitude_ft;
    }

    public void setMin_altitude_ft(Double min_altitude_ft) {
        this.min_altitude_ft = min_altitude_ft;
    }

    public Double getMax_altitude_ft() {
        return max_altitude_ft;
    }

    public void setMax_altitude_ft(Double max_altitude_ft) {
        this.max_altitude_ft = max_altitude_ft;
    }

    public JSONObject getRes_vol_props() {
        return res_vol_props;
    }

    public void setRes_vol_props(JSONObject res_vol_props) {
        this.res_vol_props = res_vol_props;
    }

    @Override
    public String toString() {
        return "{" +
                "\"reserved_volume_projection\":" + reserved_volume_projection +
                ", \"mission_notes\":\"" + mission_notes + '\"' +
                ", \"effective_time_begin\":\"" + effective_time_begin + '\"' +
                ", \"effective_time_end\":\"" + effective_time_end + '\"' +
                ", \"min_altitude_ft\":" + min_altitude_ft +
                ", \"max_altitude_ft\":" + max_altitude_ft +
                ", \"res_vol_props\":" + res_vol_props +
                '}';
    }
}
