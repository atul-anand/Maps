package com.zemoso.atul.maps.javabeans;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by zemoso on 7/9/17.
 */

public class RegistryUser extends RealmObject {
    @NonNull
    private String f_name = "";
    @NonNull
    private String l_name = "";
    @NonNull
    private String username = "";
    @NonNull
    private Integer phone = 0;
    @NonNull
    private String role = "";
    private String id;
    private String alt_email;
    private String alt_phone;
    private String created_at;
    private String created_by;
    private Boolean is_deleted;
    private String modified_at;
    private String modified_by;
    @Ignore
    private JSONObject other_info;
    private String role_id;

    public RegistryUser() {
    }

    public RegistryUser(JSONObject jsonObject) {
        this.f_name = jsonObject.optString("f_name");
        this.l_name = jsonObject.optString("l_name");
        this.username = jsonObject.optString("username");
        this.phone = jsonObject.optInt("phone");
        this.role = jsonObject.optString("role");
        this.id = jsonObject.optString("id");
        this.alt_email = jsonObject.optString("alt_email");
        this.alt_phone = jsonObject.optString("alt_phone");
        this.created_at = jsonObject.optString("created_at");
        this.created_by = jsonObject.optString("created_by");
        this.is_deleted = jsonObject.optBoolean("is_deleted");
        this.modified_at = jsonObject.optString("modified_at");
        this.modified_by = jsonObject.optString("modified_by");
        this.other_info = jsonObject.optJSONObject("other_info");
        this.role_id = jsonObject.optString("role_id");
    }

    @NonNull
    public String getF_name() {
        return f_name;
    }

    public void setF_name(@NonNull String f_name) {
        this.f_name = f_name;
    }

    @NonNull
    public String getL_name() {
        return l_name;
    }

    public void setL_name(@NonNull String l_name) {
        this.l_name = l_name;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public Integer getPhone() {
        return phone;
    }

    public void setPhone(@NonNull Integer phone) {
        this.phone = phone;
    }

    @NonNull
    public String getRole() {
        return role;
    }

    public void setRole(@NonNull String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlt_email() {
        return alt_email;
    }

    public void setAlt_email(String alt_email) {
        this.alt_email = alt_email;
    }

    public String getAlt_phone() {
        return alt_phone;
    }

    public void setAlt_phone(String alt_phone) {
        this.alt_phone = alt_phone;
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

    public JSONObject getOther_info() {
        return other_info;
    }

    public void setOther_info(JSONObject other_info) {
        this.other_info = other_info;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"f_name\":\"" + f_name + '\"' +
                ", \"l_name\":\"" + l_name + '\"' +
                ", \"username\":\"" + username + '\"' +
                ", \"phone\":" + phone +
                ", \"role\":\"" + role + '\"' +
                ", \"id\":\"" + id + '\"' +
                ", \"alt_email\":\"" + alt_email + '\"' +
                ", \"alt_phone\":\"" + alt_phone + '\"' +
                ", \"created_at\":\"" + created_at + '\"' +
                ", \"created_by\":\"" + created_by + '\"' +
                ", \"is_deleted\":" + is_deleted +
                ", \"modified_at\":\"" + modified_at + '\"' +
                ", \"modified_by\":\"" + modified_by + '\"' +
                ", \"other_info\":" + other_info +
                ", \"role_id\":\"" + role_id + '\"' +
                '}';
    }
}
