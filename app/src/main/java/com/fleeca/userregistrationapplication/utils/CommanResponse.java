package com.fleeca.userregistrationapplication.utils;

public class CommanResponse {
    private String status;
    private String message;

    public String isStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

