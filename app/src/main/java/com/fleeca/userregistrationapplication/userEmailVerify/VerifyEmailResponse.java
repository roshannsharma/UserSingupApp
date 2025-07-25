package com.fleeca.userregistrationapplication.userEmailVerify;

import com.fleeca.userregistrationapplication.utils.CommanResponse;

public class VerifyEmailResponse extends CommanResponse {
    private UserEmailVerfiyData data;
    private String error;

    public UserEmailVerfiyData getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public class UserEmailVerfiyData {
        private boolean is_verified;

        public boolean isVerified() {
            return is_verified;
        }
    }
}
