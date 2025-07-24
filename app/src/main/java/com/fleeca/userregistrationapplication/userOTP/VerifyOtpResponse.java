package com.fleeca.userregistrationapplication.userOTP;

import com.fleeca.userregistrationapplication.utils.CommanResponse;

import java.util.List;

// VerifyOtpResponse.java
public class VerifyOtpResponse extends CommanResponse {


    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}

