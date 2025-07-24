package com.fleeca.userregistrationapplication.userOTP;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fleeca.userregistrationapplication.UserEmailVerify.UserEmailVerifyRepository;

public class OTPViewModel extends ViewModel {
    private final OTPRepository otpRepository;
    private final MutableLiveData<VerifyOtpResponse> VerifyOtpResponse = new MutableLiveData<>();

    public OTPViewModel() {
        otpRepository = new OTPRepository();
    }

    public void verifyOTP(String otp, String token) {
        otpRepository.verifyOtp(otp, token).observeForever(response -> {
            VerifyOtpResponse.setValue(response);
        });
    }


    public LiveData<VerifyOtpResponse> getVerifyOTPResponse() {
        return VerifyOtpResponse;
    }

}
