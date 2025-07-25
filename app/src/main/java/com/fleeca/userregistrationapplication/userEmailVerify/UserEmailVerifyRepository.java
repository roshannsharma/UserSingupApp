package com.fleeca.userregistrationapplication.userEmailVerify;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fleeca.userregistrationapplication.api.ApiClient;
import com.fleeca.userregistrationapplication.api.ApiService;
import com.fleeca.userregistrationapplication.userOTP.SendOtpRequest;
import com.fleeca.userregistrationapplication.userOTP.SendOtpResponse;
import com.fleeca.userregistrationapplication.utils.ApiErrorHandler;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class UserEmailVerifyRepository {
    private final ApiService apiService;

    public UserEmailVerifyRepository() {
        apiService = ApiClient.getApiService("");
    }

    public LiveData<VerifyEmailResponse> verifyUserEmail(String email) {
        VerifyEmailRequest request = new VerifyEmailRequest(email);
        MutableLiveData<VerifyEmailResponse> data = new MutableLiveData<>();
        apiService.verifyUserEmail(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            data.setValue(response);
                        },
                        throwable -> {
                            VerifyEmailResponse error = ApiErrorHandler.handle(throwable, new VerifyEmailResponse());
                            data.postValue(error);
                        }
                );

        return data;
    }

    public LiveData<SendOtpResponse> sendOTP(String email) {
        SendOtpRequest request = new SendOtpRequest(email);
        MutableLiveData<SendOtpResponse> data = new MutableLiveData<>();
        apiService.sendOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            data.setValue(response);
                        },
                        throwable -> {
                            SendOtpResponse error = ApiErrorHandler.handle(throwable, new SendOtpResponse());
                            data.postValue(error);
                        }
                );

        return data;
    }

}

