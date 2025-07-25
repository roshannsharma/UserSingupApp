package com.fleeca.userregistrationapplication.userEmailVerify;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fleeca.userregistrationapplication.userOTP.SendOtpResponse;

public class UserEmailVerfiyViewModel extends ViewModel {
    private final UserEmailVerifyRepository userEmailVerifyRepository;
    private final MutableLiveData<VerifyEmailResponse> verifyEmailResponse = new MutableLiveData<>();

    private final MutableLiveData<SendOtpResponse> sendOtpResponse = new MutableLiveData<>();

    private final MutableLiveData<GetUserDetailsByTokenResponse> getUserDetailsByTokenResponse = new MutableLiveData<>();

    public final MutableLiveData<Boolean> btNextClick = new MutableLiveData<>(false);

    public final MutableLiveData<String> email = new MutableLiveData<>("");
    public final MutableLiveData<String> password = new MutableLiveData<>("");

    public final MediatorLiveData<Boolean> isFormValid = new MediatorLiveData<>();

    private void checkFormValidity() {
        String inputEmial = email.getValue();
        String inputPassword = password.getValue();

        boolean valid = inputEmial != null && !inputEmial.trim().isEmpty()
                && inputPassword != null && !inputPassword.trim().isEmpty();

        isFormValid.setValue(valid);
    }

    public UserEmailVerfiyViewModel() {
        userEmailVerifyRepository = new UserEmailVerifyRepository();
        isFormValid.setValue(false);

        isFormValid.addSource(email, e -> checkFormValidity());
        isFormValid.addSource(password, p -> checkFormValidity());
    }

    public void clickOnBt() {
        btNextClick.setValue(true);
    }

    public void resetClickEvent() {
        btNextClick.setValue(false); // reset after handling
    }

    public void verifyEmail(String email) {
        userEmailVerifyRepository.verifyUserEmail(email).observeForever(response -> {
            verifyEmailResponse.setValue(response);
        });
    }

    public void sendOTP(String email) {
        userEmailVerifyRepository.sendOTP(email).observeForever(response -> {
            sendOtpResponse.setValue(response);
        });
    }


    public LiveData<VerifyEmailResponse> getVerifyEmailResponse() {
        return verifyEmailResponse;
    }

    public LiveData<SendOtpResponse> getSendOtpResponse() {
        return sendOtpResponse;
    }

}
