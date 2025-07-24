package com.fleeca.userregistrationapplication.userProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserRegistrationViewModel extends ViewModel {

    private UserRegistrationRepository repository;
    private final MutableLiveData<UserRegistrationResponse> userRegistrationResponse = new MutableLiveData<>();


    public UserRegistrationViewModel() {
        repository = new UserRegistrationRepository();
    }



    public void registerUser(UserRegistrationRequest request) {
        repository.registerUser(request).observeForever(response -> {
            userRegistrationResponse.setValue(response);
        });
    }
    public LiveData<UserRegistrationResponse> getUserRegistrationResponse() {
        return userRegistrationResponse;
    }

}

