package com.fleeca.userregistrationapplication.userEmailVerify;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fleeca.userregistrationapplication.R;
import com.fleeca.userregistrationapplication.databinding.ActivityMainBinding;
import com.fleeca.userregistrationapplication.userOTP.OTPActivity;
import com.fleeca.userregistrationapplication.utils.CustomProgressBar;
import com.fleeca.userregistrationapplication.utils.PreferenceManger;
import com.fleeca.userregistrationapplication.utils.ValidationUtil;

public class UserSignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mBinding;
    private UserEmailVerfiyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        viewModel = new ViewModelProvider(this).get(UserEmailVerfiyViewModel.class);
        mBinding.setViewModel(viewModel);
        mBinding.setLifecycleOwner(this);
        uiBind();
    }

    private void uiBind() {
        mBinding.btnNext.setEnabled(false);
        mBinding.btnNext.setBackgroundResource(R.drawable.bt_submit_button_disabled);
        observeClicks();

    }

    private void observeClicks() {
        viewModel.isFormValid.observe(this, isValid -> {
            mBinding.btnNext.setEnabled(isValid);
            mBinding.btnNext.setBackgroundResource(isValid
                    ? R.drawable.bt_submit_button_background
                    : R.drawable.bt_submit_button_disabled);
        });

        viewModel.btNextClick.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean clicked) {
                if (Boolean.TRUE.equals(clicked)) {
                    doSignupValidation();
                    viewModel.resetClickEvent();
                }
            }
        });
    }


    public void doSignupValidation() {
        String email = viewModel.email.getValue().toString().trim();
        String password = viewModel.password.getValue().toString().trim();

        if (!ValidationUtil.isValidEmail(email)) {
            mBinding.emailError.setText(R.string.invalid_email_format);
            return;
        }
        if (!ValidationUtil.isValidPassword(password)) {
            mBinding.passwordError.setText(R.string.password_must_be_8_characters_include_1_uppercase_1_number);
            return;
        }

        mBinding.emailError.setText("");
        mBinding.passwordError.setText("");
        mBinding.etEmailAddress.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        if (ValidationUtil.isNetworkAvailable()) {
            apiCallingIsEmailVerify(email);
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNext) {
            //doSignupValidation();
        }
    }

    private void apiCallingIsEmailVerify(String email) {
        CustomProgressBar loaderDialog = new CustomProgressBar(this);
        loaderDialog.show();
        viewModel.verifyEmail(email);

        viewModel.getVerifyEmailResponse().observe(this, response -> {
            loaderDialog.hide();
            if (response.isStatus().equals("success")) {
                boolean isVerified = response.getData().isVerified();
                Log.d("API", "is_verified: " + isVerified);
                if (isVerified) {
                    setEmailVerfityIcon(true);
                    Toast.makeText(this, "Email Verified", Toast.LENGTH_SHORT).show();
                    PreferenceManger.setUserEmail(viewModel.email.getValue().toString().trim());

                    if (ValidationUtil.isNetworkAvailable()) {
                        apiCallingOTPSend(email);
                    } else {
                        Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // show email not verified message
                    setEmailVerfityIcon(false);
                    Toast.makeText(this, "Email not Verified", Toast.LENGTH_SHORT).show();


                }
            } else {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void apiCallingOTPSend(String email) {
        CustomProgressBar loaderDialog = new CustomProgressBar(this);
        loaderDialog.show();
        viewModel.sendOTP(email);
        viewModel.getSendOtpResponse().observe(this, response -> {
            loaderDialog.hide();
            if (response.isStatus().equals("success")) {
                String optMsg = response.getData().getMessage();
                Log.d("API", "optMsg: " + optMsg);
                Toast.makeText(this, optMsg, Toast.LENGTH_SHORT).show();

                PreferenceManger.setToken(response.getData().getToken().toString());
                PreferenceManger.setUserPassword(viewModel.password.getValue().toString().trim());
                PreferenceManger.setUserEmail(viewModel.email.getValue().toString().trim());

                Intent intent = new Intent(this, OTPActivity.class);
                intent.putExtra("SCREEN_MODE", "1");
                startActivity(intent);
            } else {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    private void setEmailVerfityIcon(boolean value) {
        if (value) {
            Drawable verifiedIcon = ContextCompat.getDrawable(this, R.drawable.ic_verified);
            mBinding.etEmailAddress.setCompoundDrawablesWithIntrinsicBounds(null, null, verifiedIcon, null);
        } else {
            Drawable unVerifiedIcon = ContextCompat.getDrawable(this, R.drawable.ic_unverified);
            mBinding.etEmailAddress.setCompoundDrawablesWithIntrinsicBounds(null, null, unVerifiedIcon, null);
        }
    }
}