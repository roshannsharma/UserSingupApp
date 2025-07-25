package com.fleeca.userregistrationapplication.userOTP;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OTPViewModel extends ViewModel {
    private final OTPRepository otpRepository;
    private final MutableLiveData<VerifyOtpResponse> VerifyOtpResponse = new MutableLiveData<>();

    public final MutableLiveData<Boolean> btNextClick = new MutableLiveData<>(false);

    public final MutableLiveData<String> otp1 = new MutableLiveData<>("");
    public final MutableLiveData<String> otp2 = new MutableLiveData<>("");
    public final MutableLiveData<String> otp3 = new MutableLiveData<>("");
    public final MutableLiveData<String> otp4 = new MutableLiveData<>("");
    public final MutableLiveData<String> otp5 = new MutableLiveData<>("");
    public final MutableLiveData<String> otp6 = new MutableLiveData<>("");

    public final MediatorLiveData<String> completeOtp = new MediatorLiveData<>();
    public final MediatorLiveData<Boolean> isOtpValid = new MediatorLiveData<>();

    private void combineOtp() {
        String otp = getVal(otp1) + getVal(otp2) + getVal(otp3) +
                getVal(otp4) + getVal(otp5) + getVal(otp6);

        completeOtp.setValue(otp);
        isOtpValid.setValue(otp.length() == 6 && otp.matches("\\d{6}"));
    }


    private String getVal(MutableLiveData<String> liveData) {
        return liveData.getValue() != null ? liveData.getValue() : "";
    }

    public void clickOnBt() {
        btNextClick.setValue(true);
    }

    public void resetClickEvent() {
        btNextClick.setValue(false); // reset after handling
    }

    public OTPViewModel() {
        otpRepository = new OTPRepository();
        completeOtp.addSource(otp1, s -> combineOtp());
        completeOtp.addSource(otp2, s -> combineOtp());
        completeOtp.addSource(otp3, s -> combineOtp());
        completeOtp.addSource(otp4, s -> combineOtp());
        completeOtp.addSource(otp5, s -> combineOtp());
        completeOtp.addSource(otp6, s -> combineOtp());
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
