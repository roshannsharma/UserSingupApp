package com.fleeca.userregistrationapplication.utils;

public class PreferenceManger {

    public static String getIsPRofileDone() {
        return PreferenceUtil.getPreference(PreferenceConstant.TOKEN, "0");
    }

    public static void setIsPRofileDone(String isValue) {
        PreferenceUtil.setPreference(PreferenceConstant.TOKEN, isValue);
    }

    public static String getToken() {
        return PreferenceUtil.getPreference(PreferenceConstant.TOKEN, "");
    }

    public static void setToken(String token) {
        PreferenceUtil.setPreference(PreferenceConstant.TOKEN, token);
    }

    public static String getUserPassword() {
        return PreferenceUtil.getPreference(PreferenceConstant.USER_PASSWORD, "");
    }

    public static void setUserPassword(String password) {
        PreferenceUtil.setPreference(PreferenceConstant.USER_PASSWORD, password);
    }

    public static String getUSerEmail() {
        return PreferenceUtil.getPreference(PreferenceConstant.USER_EMAIL, "");
    }

    public static void setOTPVerfiyTime(long time) {
        PreferenceUtil.setPreference(PreferenceConstant.OTP_VERIFIED_TIME, time);
    }

    public static long getOTPVerifyTime() {
        return PreferenceUtil.getPreference(PreferenceConstant.OTP_VERIFIED_TIME, 0L);
    }

    public static void setUserEmail(String email) {
        PreferenceUtil.setPreference(PreferenceConstant.USER_EMAIL, email);
    }

    public static void logOut() {
        PreferenceUtil.clearAllPreferences();
    }
}
