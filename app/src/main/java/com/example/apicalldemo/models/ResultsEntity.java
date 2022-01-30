package com.example.apicalldemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultsEntity {
    @Expose
    @SerializedName("nat")
    public String nat;
    @Expose
    @SerializedName("picture")
    public PictureEntity picture;
    @Expose
    @SerializedName("email")
    public String email;
    @Expose
    @SerializedName("location")
    public LocationEntity location;
    @Expose
    @SerializedName("name")
    public NameEntity name;
    @Expose
    @SerializedName("gender")
    public String gender;
}
