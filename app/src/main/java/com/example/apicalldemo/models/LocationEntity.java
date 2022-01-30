package com.example.apicalldemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationEntity {
    @Expose
    @SerializedName("timezone")
    public TimezoneEntity timezone;
    @Expose
    @SerializedName("postcode")
    public String postcode;
    @Expose
    @SerializedName("country")
    public String country;
    @Expose
    @SerializedName("street")
    public StreetEntity street;
}
