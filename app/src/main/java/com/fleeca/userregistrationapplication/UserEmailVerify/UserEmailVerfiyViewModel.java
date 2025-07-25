package com.fleeca.userregistrationapplication.UserEmailVerify;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fleeca.userregistrationapplication.userOTP.SendOtpResponse;

public class UserEmailVerfiyViewModel extends ViewModel {
    private final UserEmailVerifyRepository userEmailVerifyRepository;
    private final MutableLiveData<VerifyEmailResponse> verifyEmailResponse = new MutableLiveData<>();

    private final MutableLiveData<SendOtpResponse> sendOtpResponse = new MutableLiveData<>();

    private final MutableLiveData<GetUserDetailsByTokenResponse> getUserDetailsByTokenResponse = new MutableLiveData<>();




    public UserEmailVerfiyViewModel() {
        userEmailVerifyRepository = new UserEmailVerifyRepository();
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

    public void setGetUserDetailsByToken(String tokan) {
       /* userEmailVerifyRepository.getUserDetailsByTokan(tokan).observeForever(response -> {
            getUserDetailsByTokenResponse.setValue(response);
        });*/
    }

    public LiveData<VerifyEmailResponse> getVerifyEmailResponse() {
        return verifyEmailResponse;
    }

    public LiveData<SendOtpResponse> getSendOtpResponse() {
        return sendOtpResponse;
    }

    public LiveData<GetUserDetailsByTokenResponse> getUserDetailsByTokenResponsee() {
        return getUserDetailsByTokenResponse;
    }
}
