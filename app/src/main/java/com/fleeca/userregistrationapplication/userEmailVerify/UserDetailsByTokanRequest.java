package com.fleeca.userregistrationapplication.userEmailVerify;

public class UserDetailsByTokanRequest {
    private String token;

    public UserDetailsByTokanRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}


