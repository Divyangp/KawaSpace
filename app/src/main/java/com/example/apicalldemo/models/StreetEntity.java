package com.example.apicalldemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StreetEntity {
    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("number")
    public int number;
}
