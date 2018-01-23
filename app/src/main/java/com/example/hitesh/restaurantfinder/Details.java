package com.example.hitesh.restaurantfinder;

/**
 * Created by hitesh on 1/23/2018.
 */

public class Details {
    private String key;
    private String value;

    public Details(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
