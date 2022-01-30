package com.example.apicalldemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimezoneEntity {
    @Expose
    @SerializedName("description")
    public String description;
    @Expose
    @SerializedName("offset")
    public String offset;
}
