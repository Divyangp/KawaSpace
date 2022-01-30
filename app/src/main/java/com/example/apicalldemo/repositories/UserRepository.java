package com.example.apicalldemo.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apicalldemo.apis.RandomUserService;
import com.example.apicalldemo.models.UserResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private static final String BASE_URL = "https://randomuser.me/";

    private final RandomUserService randomUserService;
    private final MutableLiveData<UserResponse> userResponseLiveData;

    public UserRepository() {
        userResponseLiveData = new MutableLiveData<>();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        randomUserService = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RandomUserService.class);
    }

    public void getData(String inc, String results) {
        randomUserService.getData(inc, results)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                        if (response.body() != null) {
                            userResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable throwable) {
                        userResponseLiveData.postValue(null);
                    }
                });
    }

    public LiveData<UserResponse> getVolumesResponseLiveData() {
        return userResponseLiveData;
    }
}
