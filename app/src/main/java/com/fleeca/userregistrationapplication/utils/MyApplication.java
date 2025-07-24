package com.fleeca.userregistrationapplication.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        PreferenceUtil.init(base);
        super.attachBaseContext(base);
    }

    public static Context getContext() {
        return mContext;
    }

    public static String string(int resId) {
        return mContext.getString(resId);
    }

    public static Context context() {
        return mContext;
    }
}
