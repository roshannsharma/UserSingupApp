package com.fleeca.userregistrationapplication.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.fleeca.userregistrationapplication.R;
import com.fleeca.userregistrationapplication.UserEmailVerify.UserSignUpActivity;
import com.fleeca.userregistrationapplication.utils.PreferenceManger;
import com.fleeca.userregistrationapplication.utils.ShowCustomLoader;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ShowCustomLoader loaderDialog = new ShowCustomLoader(this);
        loaderDialog.show();
        PreferenceManger.logOut();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, UserSignUpActivity.class));
            finish();
        }, 3000); // 3 seconds
    }
}