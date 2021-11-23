package com.paysky.momogrow.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuildChartData {
    @SerializedName("Key")
    @Expose
    private String key;

    @SerializedName("Value")
    @Expose
    private Integer value;

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}