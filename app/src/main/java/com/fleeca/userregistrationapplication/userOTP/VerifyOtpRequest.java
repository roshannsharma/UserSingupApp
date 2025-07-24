package com.fleeca.userregistrationapplication.userOTP;

// VerifyOtpRequest.java
public class VerifyOtpRequest {
    private String otp;
    private String token;

    public VerifyOtpRequest(String otp, String token) {
        this.otp = otp;
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public String getToken() {
        return token;
    }
}

