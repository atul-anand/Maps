package com.zemoso.atul.maps.javabeans;

import org.json.JSONObject;

/**
 * Created by zemoso on 11/9/17.
 */

public class Contact {
    private String name;
    private Integer phone;
    private String email;

    public Contact(JSONObject jsonObject) {
        this.name = jsonObject.optString("name");
        this.phone = jsonObject.optInt("phone");
        this.email = jsonObject.optString("email");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"phone\":" + phone +
                ", \"email\":\"" + email + '\"' +
                '}';
    }
}
