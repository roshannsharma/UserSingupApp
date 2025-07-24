package com.fleeca.userregistrationapplication.UserEmailVerify;

public class VerifyEmailRequest {
    private String email;

    public VerifyEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}


