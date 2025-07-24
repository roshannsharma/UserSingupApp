package com.fleeca.userregistrationapplication.utils;

import com.fleeca.userregistrationapplication.UserEmailVerify.VerifyEmailResponse;
import com.fleeca.userregistrationapplication.userOTP.SendOtpResponse;
import com.fleeca.userregistrationapplication.userOTP.VerifyOtpResponse;
import com.fleeca.userregistrationapplication.userProfile.UserRegistrationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import javax.net.ssl.SSLProtocolException;

import retrofit2.HttpException;

public class ApiErrorHandler {

    public static <T extends CommanResponse> T handle(Throwable throwable, T errorResponseModel) {
        errorResponseModel.setStatus("failed");

        if (throwable instanceof HttpException) {
            HttpException httpEx = (HttpException) throwable;
            try {
                String errorBody = httpEx.response().errorBody().string();
                JSONObject json = new JSONObject(errorBody);

                int code = httpEx.code();

                if (code == 400 && json.has("error")) {
                    errorResponseModel.setMessage(json.getString("error"));

                } else if (code == 404 && json.has("data")) {
                    JSONObject dataObj = json.getJSONObject("data");
                    boolean isVerified = dataObj.optBoolean("is_verified", false);

                    errorResponseModel.setMessage("User not found");

                } else if (json.has("data")) {
                    JSONObject dataObj = json.getJSONObject("data");
                    String errorMessage = dataObj.optString("message", "Something went wrong.");
                    errorResponseModel.setMessage(errorMessage);

                } else {
                    errorResponseModel.setMessage("Unexpected error");
                }

            } catch (Exception e) {
                errorResponseModel.setMessage("Failed to parse error response");
            }

        } else {
            errorResponseModel.setMessage(onApiCallingFailure(throwable));
        }

        return errorResponseModel;
    }


    public static String onApiCallingFailure(Throwable t) {
        String msg;

        if (t instanceof EOFException) {
            msg = "Unexpected end of input.";
        } else if (t instanceof UnknownServiceException) {
            msg = "Unknown service. Please try again.";
        } else if (t instanceof UnknownHostException) {
            msg = "No internet connection. Please check your network.";
        } else if (t instanceof ProtocolException) {
            msg = "Protocol error occurred.";
        } else if (t instanceof JSONException) {
            msg = "Parsing error. Please try again.";
        } else if (t instanceof SSLProtocolException) {
            msg = "Secure connection error. Try again later.";
        } else if (t instanceof SocketTimeoutException) {
            msg = "Connection timed out. Please check your internet.";
        } else if (t instanceof RuntimeException) {
            msg = "Something went wrong. Please try again.";
        } else {
            msg = "Something went wrong. Please try again.";
        }
        return msg;
    }
}

