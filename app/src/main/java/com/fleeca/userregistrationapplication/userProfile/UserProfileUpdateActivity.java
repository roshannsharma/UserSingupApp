package com.fleeca.userregistrationapplication.userProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fleeca.userregistrationapplication.DashBoard.DashBoardActivity;
import com.fleeca.userregistrationapplication.R;
import com.fleeca.userregistrationapplication.databinding.ActivityUserProfileUpdateBinding;
import com.fleeca.userregistrationapplication.userOTP.OTPActivity;
import com.fleeca.userregistrationapplication.utils.PreferenceManger;
import com.fleeca.userregistrationapplication.utils.ShowCustomLoader;
import com.fleeca.userregistrationapplication.utils.ValidationUtil;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class UserProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityUserProfileUpdateBinding mBinding;
    private UserRegistrationViewModel viewModel;
    private String dobForApi;
    private final long OTP_EXPIRY_LIMIT = 10 * 60 * 1000; // 10 minutes
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityUserProfileUpdateBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        checkOtpExpiry();
        uiBind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkOtpExpiry();
    }

    private void uiBind() {

        mBinding.btnNext.setOnClickListener(this);
        mBinding.etDOB.setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(UserRegistrationViewModel.class);

        mBinding.btnNext.setEnabled(false);
        mBinding.btnNext.setBackgroundResource(R.drawable.bt_submit_button_disabled);

        TextWatcher formWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateForm();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        mBinding.etfirstName.addTextChangedListener(formWatcher);
        mBinding.etlastName.addTextChangedListener(formWatcher);

        mBinding.etPhone.addTextChangedListener(formWatcher);
        mBinding.etDOB.addTextChangedListener(formWatcher);
    }

    private void checkOtpExpiry() {
        long verifiedTime = PreferenceManger.getOTPVerifyTime();
        long currentTime = System.currentTimeMillis();
        // Check if OTP was never verified or already expired
        if (verifiedTime == 0L || (currentTime - verifiedTime > OTP_EXPIRY_LIMIT)) {
            PreferenceManger.setOTPVerfiyTime(0L); // reset
            showOtpExpiredDialog(); // redirect to OTP screen
        }
    }

    private void showOtpExpiredDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Session Expired")
                .setMessage("OTP expired! Please verify again.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(this, OTPActivity.class);
                    intent.putExtra("SCREEN_MODE", "2");
                    startActivity(intent);
                    finish();
                }).show();
    }


    private void validateForm() {
        boolean isValid = !mBinding.etfirstName.getText().toString().trim().isEmpty()
                && !mBinding.etlastName.getText().toString().trim().isEmpty()
                && !mBinding.etPhone.getText().toString().trim().isEmpty()
                && !mBinding.etDOB.getText().toString().trim().isEmpty();

        mBinding.btnNext.setEnabled(isValid);
        mBinding.btnNext.setBackgroundResource(isValid ?
                R.drawable.bt_submit_button_background :
                R.drawable.bt_submit_button_disabled);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNext) {
            if (ValidationUtil.isNetworkAvailable()) {
                doValidation();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.etDOB) {
            openCalendar(mBinding.etDOB);
        }
    }

    private void doValidation() {

        if (TextUtils.isEmpty(mBinding.etfirstName.getText().toString().trim())) {
            mBinding.firstNameError.setText(R.string.first_name_are_required);
            return;
        }
        if (TextUtils.isEmpty(mBinding.etlastName.getText().toString().trim())) {
            mBinding.lastNameError.setText(R.string.last_name_are_required);
            return;
        }
        if (TextUtils.isEmpty(mBinding.etPhone.getText().toString().trim())) {
            mBinding.etPhone.setText(R.string.phone_number_are_required);
            return;
        }
        if (TextUtils.isEmpty(mBinding.etDOB.getText().toString().trim())) {
            mBinding.DOBError.setText(R.string.dob_are_requiredd);
            return;
        }

        String gender = "Male";
        Integer languageId = 1;
        String smoke = "no";
        String exercise = "3-4 days";
        String sleep = "7-8 hours";
        String water = "8+ glasses";
        String pain = "rarely";
        String prescription = "none";
        String physicalExam = "annually";
        int userType = 0;
        boolean acceptedPrivacyPolicy = true;
        String timeZone = "America/New_York";

        List<Integer> areasOfInterest = Arrays.asList(1, 2);
        List<Integer> wellbeingPillars = Arrays.asList(1, 2, 3);

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setFname(mBinding.etfirstName.getText().toString().trim());
        userRegistrationRequest.setLname(mBinding.etlastName.getText().toString().trim());
        userRegistrationRequest.setPhone_number(mBinding.etPhone.getText().toString().trim());
        userRegistrationRequest.setBirthday(dobForApi);
        userRegistrationRequest.setToken(PreferenceManger.getToken());

        userRegistrationRequest.setAreas_of_interest(areasOfInterest);
        userRegistrationRequest.setWellbeing_pillars(wellbeingPillars);

        userRegistrationRequest.setGender(gender);
        userRegistrationRequest.setLanguage_id(languageId);
        userRegistrationRequest.setSmoke(smoke);


        userRegistrationRequest.setExercise_day_per_week(exercise);
        userRegistrationRequest.setAverage_sleep_per_night(sleep);
        userRegistrationRequest.setAverage_water_intake(water);


        userRegistrationRequest.setPain_experience(pain);
        userRegistrationRequest.setPrescription_intake(prescription);
        userRegistrationRequest.setPhysical_exam_frequency(physicalExam);
        userRegistrationRequest.setUser_type(userType);
        userRegistrationRequest.setAccepted_privacy_policy(acceptedPrivacyPolicy);
        userRegistrationRequest.setPassword(PreferenceManger.getUserPassword());
        userRegistrationRequest.setEmail(PreferenceManger.getUSerEmail());
        userRegistrationRequest.setTime_zone(timeZone);
        apiCallingUserRegistraionSave(userRegistrationRequest);

    }

    private void apiCallingUserRegistraionSave(UserRegistrationRequest userRegistrationRequest) {
        ShowCustomLoader loaderDialog = new ShowCustomLoader(this);
        loaderDialog.show();
        viewModel.registerUser(userRegistrationRequest);
        viewModel.getUserRegistrationResponse().observe(this, response -> {
            loaderDialog.hide();
            if (response.isStatus().equals("success")) {
                Intent intent = new Intent(this, DashBoardActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openCalendar(EditText etDOB) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(UserProfileUpdateActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String displayDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    etDOB.setText(displayDate);
                    String apiDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    dobForApi = apiDate;

                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // restrict to past
        datePickerDialog.show();
    }
}