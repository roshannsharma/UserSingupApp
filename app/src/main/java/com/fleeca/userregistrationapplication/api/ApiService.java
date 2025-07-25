package com.fleeca.userregistrationapplication.api;

import static com.fleeca.userregistrationapplication.utils.ApiConstant.USER_DETAILS_BY_TOKAN;
import static com.fleeca.userregistrationapplication.utils.ApiConstant.USER_REGISTRATION;

import com.fleeca.userregistrationapplication.userEmailVerify.GetUserDetailsByTokenResponse;
import com.fleeca.userregistrationapplication.userEmailVerify.UserDetailsByTokanRequest;
import com.fleeca.userregistrationapplication.userEmailVerify.VerifyEmailRequest;
import com.fleeca.userregistrationapplication.userEmailVerify.VerifyEmailResponse;
import com.fleeca.userregistrationapplication.userOTP.SendOtpRequest;
import com.fleeca.userregistrationapplication.userOTP.SendOtpResponse;
import com.fleeca.userregistrationapplication.userOTP.VerifyOtpRequest;
import com.fleeca.userregistrationapplication.userOTP.VerifyOtpResponse;
import com.fleeca.userregistrationapplication.userProfile.UserRegistrationRequest;
import com.fleeca.userregistrationapplication.userProfile.UserRegistrationResponse;
import com.fleeca.userregistrationapplication.utils.ApiConstant;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST(ApiConstant.USER_EMAIL_VERIFY)
    Single<VerifyEmailResponse> verifyUserEmail(@Body VerifyEmailRequest request);

    @POST(ApiConstant.SEND_USER_OTP)
    Single<SendOtpResponse> sendOTP(@Body SendOtpRequest request);

    @POST(ApiConstant.OTP_VERIFY)
    Single<VerifyOtpResponse> verifyOtp(@Body VerifyOtpRequest request);

    @POST(USER_REGISTRATION)
    Single<UserRegistrationResponse> registerUser(@Body UserRegistrationRequest request);

    @POST(USER_DETAILS_BY_TOKAN)
    Single<GetUserDetailsByTokenResponse> getUserDetailByTokan(@Body UserDetailsByTokanRequest request);
}


