package com.example.apicalldemo.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apicalldemo.models.UserResponse;
import com.example.apicalldemo.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<UserResponse> volumesResponseLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        userRepository = new UserRepository();
        volumesResponseLiveData = userRepository.getVolumesResponseLiveData();
    }

    public void getRandomUsers(String inc, String results) {
        userRepository.getData(inc, results);
    }

    public LiveData<UserResponse> getUserResponseLiveData() {
        return volumesResponseLiveData;
    }
}
