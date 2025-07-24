package com.fleeca.userregistrationapplication.UserEmailVerify;

public class UserDetailsByTokanRequest {
    private String token;

    public UserDetailsByTokanRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}


