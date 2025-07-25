package com.fleeca.userregistrationapplication.userOTP;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fleeca.userregistrationapplication.R;
import com.fleeca.userregistrationapplication.UserEmailVerify.UserEmailVerfiyViewModel;
import com.fleeca.userregistrationapplication.databinding.ActivityOtpactivityBinding;
import com.fleeca.userregistrationapplication.userProfile.UserProfileUpdateActivity;
import com.fleeca.userregistrationapplication.utils.PreferenceManger;
import com.fleeca.userregistrationapplication.utils.CustomProgressBar;
import com.fleeca.userregistrationapplication.utils.ValidationUtil;

import java.util.Locale;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOtpactivityBinding binding;
    private CountDownTimer countDownTimer;
    private final long RESEND_INTERVAL = 10 * 60 * 1000; // 10 minutes
    private OTPViewModel viewModel;
    private UserEmailVerfiyViewModel userEmailVerfiyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        uiBind();
        initOtpInputs();
        startResendCountdown();

        Log.d("USER_EMIAL", PreferenceManger.getUSerEmail());
        if (getIntent() != null) {
            String screenMode = getIntent().getStringExtra("SCREEN_MODE");
            if (screenMode.equals("2")) {
                Log.d("USER_EMIAL", PreferenceManger.getUSerEmail());
                apiCallingOTPResend(PreferenceManger.getUSerEmail());
            }
        }
    }

    private void uiBind() {
        binding.btnNext.setOnClickListener(this);
        viewModel = new ViewModelProvider(this).get(OTPViewModel.class);
        userEmailVerfiyViewModel = new ViewModelProvider(this).get(UserEmailVerfiyViewModel.class);

        binding.btnNext.setEnabled(false);
        binding.btnNext.setBackgroundResource(R.drawable.bt_submit_button_disabled);

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

        binding.etOtp1.addTextChangedListener(formWatcher);
        binding.etOtp2.addTextChangedListener(formWatcher);

        binding.etOtp3.addTextChangedListener(formWatcher);
        binding.etOtp4.addTextChangedListener(formWatcher);

        binding.etOtp5.addTextChangedListener(formWatcher);
        binding.etOtp6.addTextChangedListener(formWatcher);
    }

    private void validateForm() {
        boolean isValid = !binding.etOtp1.getText().toString().trim().isEmpty()
                && !binding.etOtp2.getText().toString().trim().isEmpty()
                && !binding.etOtp3.getText().toString().trim().isEmpty()
                && !binding.etOtp4.getText().toString().trim().isEmpty()
                && !binding.etOtp5.getText().toString().trim().isEmpty()
                && !binding.etOtp6.getText().toString().trim().isEmpty();

        binding.btnNext.setEnabled(isValid);
        binding.btnNext.setBackgroundResource(isValid ?
                R.drawable.bt_submit_button_background :
                R.drawable.bt_submit_button_disabled);
    }

    private void initOtpInputs() {
        EditText[] otpBoxes = new EditText[]{
                binding.etOtp1, binding.etOtp2, binding.etOtp3,
                binding.etOtp4, binding.etOtp5, binding.etOtp6
        };

        for (int i = 0; i < otpBoxes.length; i++) {
            final int current = i;
            otpBoxes[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && current < otpBoxes.length - 1) {
                        otpBoxes[current + 1].requestFocus();
                    } else if (s.length() == 0 && current > 0) {
                        otpBoxes[current - 1].requestFocus();
                    }
                }
            });
        }
    }

    private void startResendCountdown() {
        binding.tvResendOtp.setEnabled(false);

        countDownTimer = new CountDownTimer(RESEND_INTERVAL, 1000) {
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                binding.tvResendOtp.setText(String.format(Locale.getDefault(), "Resend OTP in %02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                binding.tvResendOtp.setEnabled(true);
                binding.tvResendOtp.setText("Resend OTP");
                binding.tvResendOtp.setOnClickListener(OTPActivity.this::onClick);

            }
        }.start();
    }

    private String getEnteredOtp() {
        return binding.etOtp1.getText().toString().trim() +
                binding.etOtp2.getText().toString().trim() +
                binding.etOtp3.getText().toString().trim() +
                binding.etOtp4.getText().toString().trim() +
                binding.etOtp5.getText().toString().trim() +
                binding.etOtp6.getText().toString().trim();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        binding = null;
    }


    public void onClick(View view) {
        if (view.getId() == R.id.btnNext) {
            String otpString = getEnteredOtp();

            if (ValidationUtil.isNetworkAvailable()) {
                apiCallingOTPVerify(otpString, PreferenceManger.getToken().toString());
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
        if (view.getId() == R.id.tvResendOtp) {

            apiCallingOTPResend(PreferenceManger.getUSerEmail().toString());
            startResendCountdown();

        }
    }

    private void apiCallingOTPVerify(String otp, String tokan) {
        CustomProgressBar loaderDialog = new CustomProgressBar(this);
        loaderDialog.show();
        viewModel.verifyOTP(otp, tokan);
        viewModel.getVerifyOTPResponse().observe(this, response -> {
            loaderDialog.hide();
            if (response.isStatus().equals("success")) {
                String optMsg = response.getData().get(0);
                Log.d("API", "optMsg: " + optMsg);
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                PreferenceManger.setOTPVerfiyTime(0L);
                PreferenceManger.setOTPVerfiyTime(System.currentTimeMillis());

                Toast.makeText(this, optMsg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, UserProfileUpdateActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void apiCallingOTPResend(String email) {
        CustomProgressBar loaderDialog = new CustomProgressBar(this);
        loaderDialog.show();
        userEmailVerfiyViewModel.sendOTP(email);
        userEmailVerfiyViewModel.getSendOtpResponse().observe(this, response -> {
            loaderDialog.hide();
            if (response.isStatus().equals("success")) {
                String optMsg = response.getData().getMessage();
                Log.d("API", "optMsg: " + optMsg);
                Toast.makeText(this, optMsg, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}