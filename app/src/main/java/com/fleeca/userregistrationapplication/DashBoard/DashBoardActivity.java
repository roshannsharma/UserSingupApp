package com.fleeca.userregistrationapplication.DashBoard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fleeca.userregistrationapplication.R;
import com.fleeca.userregistrationapplication.databinding.ActivityDashBoardBinding;
import com.fleeca.userregistrationapplication.splash.SplashActivity;
import com.fleeca.userregistrationapplication.utils.PreferenceManger;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityDashBoardBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout) {
            PreferenceManger.logOut();
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();

        }
    }
}