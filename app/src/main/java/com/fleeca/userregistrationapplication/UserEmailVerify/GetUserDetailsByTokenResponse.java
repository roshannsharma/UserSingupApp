package com.fleeca.userregistrationapplication.UserEmailVerify;


import com.google.gson.annotations.SerializedName;

public class GetUserDetailsByTokenResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private Data data;

    public boolean isStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("id")
        private int id;

        @SerializedName("user_email")
        private String userEmail;

        @SerializedName("fname")
        private String firstName;

        @SerializedName("lname")
        private String lastName;

        @SerializedName("name")
        private String name;

        @SerializedName("is_user_register")
        private int isUserRegister;

        @SerializedName("company_name")
        private String companyName;

        @SerializedName("verified_at")
        private String verifiedAt;

        // Getters
        public int getId() {
            return id;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getName() {
            return name;
        }

        public int getIsUserRegister() {
            return isUserRegister;
        }

        public String getCompanyName() {
            return companyName;
        }

        public String getVerifiedAt() {
            return verifiedAt;
        }
    }
}

