package com.zemoso.atul.maps.javabeans;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class OperationalVolume {

    private GeoPolygon contract_volume_projection;
    private GeoPolygon conformance_geography;
    private Integer min_altitude_wgs84_ft;
    private Integer max_altitude_wgs84_ft;
    private Integer conform_min_altitude_wgs84_ft;
    private Integer conform_max_altitude_wgs84_ft;
    private String effective_time_begin;
    private String effective_time_end;
    private String conform_time_begin;
    private String conform_time_end;
    private Integer min_speed_kn;
    private Integer max_speed_kn;
    private String actual_time_end;
    private JSONArray op_vol_props;

    public OperationalVolume(JSONObject jsonObject) {
        this.contract_volume_projection = new GeoPolygon(jsonObject.optJSONObject("contract_volume_projection"));
        this.conformance_geography = new GeoPolygon(jsonObject.optJSONObject("conformance_geography"));
        this.min_altitude_wgs84_ft = jsonObject.optInt("min_altitude_wgs84_ft");
        this.max_altitude_wgs84_ft = jsonObject.optInt("max_altitude_wgs84_ft");
        this.conform_min_altitude_wgs84_ft = jsonObject.optInt("conform_min_altitude_wgs84_ft");
        this.conform_max_altitude_wgs84_ft = jsonObject.optInt("conform_max_altitude_wgs84_ft");
        this.effective_time_begin = jsonObject.optString("effective_time_begin");
        this.effective_time_end = jsonObject.optString("effective_time_end");
        this.conform_time_begin = jsonObject.optString("conform_time_begin");
        this.conform_time_end = jsonObject.optString("conform_time_end");
        this.min_speed_kn = jsonObject.optInt("min_speed_kn");
        this.max_speed_kn = jsonObject.optInt("max_speed_kn");
        this.actual_time_end = jsonObject.optString("actual_time_end");
        this.op_vol_props = jsonObject.optJSONArray("op_vol_props");
    }

    public GeoPolygon getContract_volume_projection() {
        return contract_volume_projection;
    }

    public void setContract_volume_projection(GeoPolygon contract_volume_projection) {
        this.contract_volume_projection = contract_volume_projection;
    }

    public GeoPolygon getConformance_geography() {
        return conformance_geography;
    }

    public void setConformance_geography(GeoPolygon conformance_geography) {
        this.conformance_geography = conformance_geography;
    }

    public Integer getMin_altitude_wgs84_ft() {
        return min_altitude_wgs84_ft;
    }

    public void setMin_altitude_wgs84_ft(Integer min_altitude_wgs84_ft) {
        this.min_altitude_wgs84_ft = min_altitude_wgs84_ft;
    }

    public Integer getMax_altitude_wgs84_ft() {
        return max_altitude_wgs84_ft;
    }

    public void setMax_altitude_wgs84_ft(Integer max_altitude_wgs84_ft) {
        this.max_altitude_wgs84_ft = max_altitude_wgs84_ft;
    }

    public Integer getConform_min_altitude_wgs84_ft() {
        return conform_min_altitude_wgs84_ft;
    }

    public void setConform_min_altitude_wgs84_ft(Integer conform_min_altitude_wgs84_ft) {
        this.conform_min_altitude_wgs84_ft = conform_min_altitude_wgs84_ft;
    }

    public Integer getConform_max_altitude_wgs84_ft() {
        return conform_max_altitude_wgs84_ft;
    }

    public void setConform_max_altitude_wgs84_ft(Integer conform_max_altitude_wgs84_ft) {
        this.conform_max_altitude_wgs84_ft = conform_max_altitude_wgs84_ft;
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

    public String getConform_time_begin() {
        return conform_time_begin;
    }

    public void setConform_time_begin(String conform_time_begin) {
        this.conform_time_begin = conform_time_begin;
    }

    public String getConform_time_end() {
        return conform_time_end;
    }

    public void setConform_time_end(String conform_time_end) {
        this.conform_time_end = conform_time_end;
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

    public String getActual_time_end() {
        return actual_time_end;
    }

    public void setActual_time_end(String actual_time_end) {
        this.actual_time_end = actual_time_end;
    }

    public JSONArray getOp_vol_props() {
        return op_vol_props;
    }

    public void setOp_vol_props(JSONArray op_vol_props) {
        this.op_vol_props = op_vol_props;
    }

    @Override
    public String toString() {
        return "{" +
                "\"contract_volume_projection\":" + contract_volume_projection +
                ", \"conformance_geography\":" + conformance_geography +
                ", \"min_altitude_wgs84_ft\":" + min_altitude_wgs84_ft +
                ", \"max_altitude_wgs84_ft\":" + max_altitude_wgs84_ft +
                ", \"conform_min_altitude_wgs84_ft\":" + conform_min_altitude_wgs84_ft +
                ", \"conform_max_altitude_wgs84_ft\":" + conform_max_altitude_wgs84_ft +
                ", \"effective_time_begin\":\"" + effective_time_begin + '\"' +
                ", \"effective_time_end\":\"" + effective_time_end + '\"' +
                ", \"conform_time_begin\":\"" + conform_time_begin + '\"' +
                ", \"conform_time_end\":\"" + conform_time_end + '\"' +
                ", \"min_speed_kn\":" + min_speed_kn +
                ", \"max_speed_kn\":" + max_speed_kn +
                ", \"actual_time_end\":\"" + actual_time_end + '\"' +
                ", \"op_vol_props\":" + op_vol_props +
                '}';
    }
}
