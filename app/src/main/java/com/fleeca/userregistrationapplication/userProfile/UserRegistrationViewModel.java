package com.fleeca.userregistrationapplication.userProfile;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserRegistrationViewModel extends ViewModel {

    private UserRegistrationRepository repository;
    private final MutableLiveData<UserRegistrationResponse> userRegistrationResponse = new MutableLiveData<>();

    public final MutableLiveData<Boolean> btNextClick = new MutableLiveData<>(false);
    public MutableLiveData<String> firstName = new MutableLiveData<>("");
    public MutableLiveData<String> lastName = new MutableLiveData<>("");
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>("");
    public MutableLiveData<String> dob = new MutableLiveData<>("");

    public MediatorLiveData<Boolean> isFormValid = new MediatorLiveData<>(false);


    private void validateForm() {
        boolean isValid =
                !TextUtils.isEmpty(firstName.getValue()) &&
                        !TextUtils.isEmpty(lastName.getValue()) &&
                        !TextUtils.isEmpty(phoneNumber.getValue()) &&
                        phoneNumber.getValue().length() == 10 &&
                        !TextUtils.isEmpty(dob.getValue());

        isFormValid.setValue(isValid);
    }


    public void clickOnBt() {
        btNextClick.setValue(true);
    }

    public void resetClickEvent() {
        btNextClick.setValue(false); // reset after handling
    }

    public UserRegistrationViewModel() {
        repository = new UserRegistrationRepository();
        isFormValid.addSource(firstName, value -> validateForm());
        isFormValid.addSource(lastName, value -> validateForm());
        isFormValid.addSource(phoneNumber, value -> validateForm());
        isFormValid.addSource(dob, value -> validateForm());
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

