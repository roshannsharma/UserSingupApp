package com.fleeca.userregistrationapplication.userProfile;

import com.fleeca.userregistrationapplication.utils.CommanResponse;

public class UserRegistrationResponse extends CommanResponse {
    private Data data;
    private String error;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class Data {
        private String token;
        private User user;
        private String code;


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public static class User {
            private int uid;
            private String fname;
            private String lname;
            private String email;
            private String user_name;
            private int company_id;
            private int is_email_verified;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getFname() {
                return fname;
            }

            public void setFname(String fname) {
                this.fname = fname;
            }

            public String getLname() {
                return lname;
            }

            public void setLname(String lname) {
                this.lname = lname;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

            public int getIs_email_verified() {
                return is_email_verified;
            }

            public void setIs_email_verified(int is_email_verified) {
                this.is_email_verified = is_email_verified;
            }

        }

    }

}

