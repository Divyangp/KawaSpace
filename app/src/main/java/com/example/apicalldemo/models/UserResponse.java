package com.example.apicalldemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @Expose
    @SerializedName("results")
    public List<ResultsEntity> results;
}
