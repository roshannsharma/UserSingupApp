package com.fleeca.userregistrationapplication.userEmailVerify;

public class VerifyEmailRequest {
    private String email;

    public VerifyEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}


