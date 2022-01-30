package com.example.apicalldemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NameEntity {
    @Expose
    @SerializedName("last")
    public String last;
    @Expose
    @SerializedName("first")
    public String first;
    @Expose
    @SerializedName("title")
    public String title;
}
