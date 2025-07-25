package com.fleeca.userregistrationapplication.userOTP;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fleeca.userregistrationapplication.R;
import com.fleeca.userregistrationapplication.databinding.ActivityOtpactivityBinding;
import com.fleeca.userregistrationapplication.userEmailVerify.UserEmailVerfiyViewModel;
import com.fleeca.userregistrationapplication.userProfile.UserProfileUpdateActivity;
import com.fleeca.userregistrationapplication.utils.CustomProgressBar;
import com.fleeca.userregistrationapplication.utils.PreferenceManger;
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
        viewModel = new ViewModelProvider(this).get(OTPViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        uiBind();
        setupOtpFocusListeners();
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
        userEmailVerfiyViewModel = new ViewModelProvider(this).get(UserEmailVerfiyViewModel.class);
        binding.btnNext.setEnabled(false);
        binding.btnNext.setBackgroundResource(R.drawable.bt_submit_button_disabled);
        observeClicks();

    }

    private void observeClicks() {
        viewModel.btNextClick.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean clicked) {
                if (Boolean.TRUE.equals(clicked)) {
                    String otp = viewModel.completeOtp.getValue();
                    if (ValidationUtil.isNetworkAvailable()) {
                        apiCallingOTPVerify(otp, PreferenceManger.getToken().toString());
                    } else {
                        Toast.makeText(OTPActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                    viewModel.resetClickEvent();
                }
            }
        });
        viewModel.completeOtp.observe(this, otp -> {
            Log.d("OTP", "Current OTP: " + otp);
        });


        viewModel.isOtpValid.observe(this, isValid -> {
            binding.btnNext.setEnabled(isValid != null && isValid);
            binding.btnNext.setBackgroundResource(isValid != null && isValid
                    ? R.drawable.bt_submit_button_background
                    : R.drawable.bt_submit_button_disabled);
        });
    }

    private void setupOtpFocusListeners() {
        EditText[] editTexts = new EditText[]{
                binding.etOtp1, binding.etOtp2, binding.etOtp3,
                binding.etOtp4, binding.etOtp5, binding.etOtp6
        };

        for (int i = 0; i < editTexts.length; i++) {
            final int index = i;

            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && index < editTexts.length - 1) {
                        editTexts[index + 1].requestFocus();
                    }
                }
            });

            editTexts[i].setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_DEL &&
                        editTexts[index].getText().toString().isEmpty() &&
                        index > 0) {

                    editTexts[index - 1].requestFocus();
                    return true;
                }
                return false;
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