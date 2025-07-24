package com.fleeca.userregistrationapplication.userProfile;

import java.util.List;

public class UserRegistrationRequest {
    private String fname;
    private String lname;
    private String password;
    private String time_zone;
    private String token;
    private List<Integer> areas_of_interest;
    private List<Integer> wellbeing_pillars;
    private boolean accepted_privacy_policy;
    private String birthday;
    private String phone_number;
    private int user_type;

    private String gender;
    private String profile_image;
    private Integer language_id;
    private String smoke;
    private String exercise_day_per_week;
    private String average_sleep_per_night;
    private String average_water_intake;
    private String pain_experience;
    private String prescription_intake;
    private String physical_exam_frequency;
    private String email;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getAreas_of_interest() {
        return areas_of_interest;
    }

    public void setAreas_of_interest(List<Integer> areas_of_interest) {
        this.areas_of_interest = areas_of_interest;
    }

    public List<Integer> getWellbeing_pillars() {
        return wellbeing_pillars;
    }

    public void setWellbeing_pillars(List<Integer> wellbeing_pillars) {
        this.wellbeing_pillars = wellbeing_pillars;
    }

    public boolean isAccepted_privacy_policy() {
        return accepted_privacy_policy;
    }

    public void setAccepted_privacy_policy(boolean accepted_privacy_policy) {
        this.accepted_privacy_policy = accepted_privacy_policy;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public Integer getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Integer language_id) {
        this.language_id = language_id;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getExercise_day_per_week() {
        return exercise_day_per_week;
    }

    public void setExercise_day_per_week(String exercise_day_per_week) {
        this.exercise_day_per_week = exercise_day_per_week;
    }

    public String getAverage_sleep_per_night() {
        return average_sleep_per_night;
    }

    public void setAverage_sleep_per_night(String average_sleep_per_night) {
        this.average_sleep_per_night = average_sleep_per_night;
    }

    public String getAverage_water_intake() {
        return average_water_intake;
    }

    public void setAverage_water_intake(String average_water_intake) {
        this.average_water_intake = average_water_intake;
    }

    public String getPain_experience() {
        return pain_experience;
    }

    public void setPain_experience(String pain_experience) {
        this.pain_experience = pain_experience;
    }

    public String getPrescription_intake() {
        return prescription_intake;
    }

    public void setPrescription_intake(String prescription_intake) {
        this.prescription_intake = prescription_intake;
    }

    public String getPhysical_exam_frequency() {
        return physical_exam_frequency;
    }

    public void setPhysical_exam_frequency(String physical_exam_frequency) {
        this.physical_exam_frequency = physical_exam_frequency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

