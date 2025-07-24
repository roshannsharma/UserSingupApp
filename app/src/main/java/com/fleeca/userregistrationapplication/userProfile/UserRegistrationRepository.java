package com.fleeca.userregistrationapplication.userProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fleeca.userregistrationapplication.api.ApiClient;
import com.fleeca.userregistrationapplication.api.ApiService;
import com.fleeca.userregistrationapplication.utils.ApiErrorHandler;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserRegistrationRepository {
    private ApiService apiService;

    public UserRegistrationRepository() {
        apiService = ApiClient.getApiService("");
    }

    public LiveData<UserRegistrationResponse> registerUser(UserRegistrationRequest request) {
        MutableLiveData<UserRegistrationResponse> data = new MutableLiveData<>();
        apiService.registerUser(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> data.setValue(response),
                        throwable -> {
                            UserRegistrationResponse error = ApiErrorHandler.handle(throwable, new UserRegistrationResponse());
                            data.postValue(error);

                        }
                );

        return data;
    }
}
