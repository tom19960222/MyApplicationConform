package com.example.myapplicationconform;

import java.util.HashMap;
import java.util.Map;

public class userSchema {
    private Integer Uid;
    private String name;
    private Integer permission;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getUid() {
        return Uid;
    }


    public void setUid(Integer uid) {
        this.Uid = uid;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
