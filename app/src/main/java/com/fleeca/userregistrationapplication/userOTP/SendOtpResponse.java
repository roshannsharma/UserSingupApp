package com.fleeca.userregistrationapplication.userOTP;

import com.fleeca.userregistrationapplication.utils.CommanResponse;

public class SendOtpResponse extends CommanResponse {


    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String message;
        private String token;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

