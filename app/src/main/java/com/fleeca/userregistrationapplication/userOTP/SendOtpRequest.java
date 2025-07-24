package com.fleeca.userregistrationapplication.userOTP;

public class SendOtpRequest {
    private String email;

    public SendOtpRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

