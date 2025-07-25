package com.fleeca.userregistrationapplication.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.fleeca.userregistrationapplication.DashBoard.DashBoardActivity;
import com.fleeca.userregistrationapplication.R;
import com.fleeca.userregistrationapplication.UserEmailVerify.UserSignUpActivity;
import com.fleeca.userregistrationapplication.utils.PreferenceManger;
import com.fleeca.userregistrationapplication.utils.CustomProgressBar;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CustomProgressBar loaderDialog = new CustomProgressBar(this);
        loaderDialog.show();

        goToNextScreen();


    }

    private void goToNextScreen() {
        new Handler().postDelayed(() -> {
            if (PreferenceManger.getIsPRofileDone().equals("1") && PreferenceManger.getToken() != null) {
                // User is fully registered → go to Dashboard
                Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            } /*else if (PreferenceManger.getToken() != null && PreferenceManger.getUSerEmail() != null) {
                // User has token, but profile is incomplete → go to OTP screen
                Intent intent = new Intent(SplashActivity.this, OTPActivity.class);
                intent.putExtra("SCREEN_MODE", "2");
                startActivity(intent);
                finish();
            }*/ else {
                // New user → go to Signup
                Intent intent = new Intent(SplashActivity.this, UserSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // Delay of 3 seconds for all cases

    }
}