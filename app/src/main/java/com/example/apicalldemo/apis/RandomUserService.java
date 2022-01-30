package com.example.apicalldemo.apis;

import com.example.apicalldemo.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserService {
    @GET("/api")
    Call<UserResponse> getData(
            @Query("inc") String inc,
            @Query("results") String results
    );
}
