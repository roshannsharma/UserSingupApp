package com.fleeca.userregistrationapplication.userOTP;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fleeca.userregistrationapplication.api.ApiClient;
import com.fleeca.userregistrationapplication.api.ApiService;
import com.fleeca.userregistrationapplication.utils.ApiErrorHandler;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OTPRepository {
    private ApiService apiService;

    public OTPRepository() {
        apiService = ApiClient.getApiService("");
    }

    public LiveData<VerifyOtpResponse> verifyOtp(String otp, String token) {
        MutableLiveData<VerifyOtpResponse> data = new MutableLiveData<>();

        VerifyOtpRequest request = new VerifyOtpRequest(otp, token);
        apiService.verifyOtp(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> data.postValue(response),
                        throwable -> {
                            VerifyOtpResponse error = ApiErrorHandler.handle(throwable, new VerifyOtpResponse());
                            data.postValue(error);

                        }
                );

        return data;
    }
}
