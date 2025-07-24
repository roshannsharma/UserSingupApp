

package com.fleeca.userregistrationapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PreferenceUtil {
    private static SharedPreferences sharedPreferences;
    private static PreferenceUtil preferenceUtil;

    public static void init(Context appContext) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        preferenceUtil = new PreferenceUtil();
    }

    public static PreferenceUtil getInstance() {
        if (preferenceUtil == null)
            preferenceUtil = new PreferenceUtil();
        return preferenceUtil;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPreference(String key, T defaultValue) {
        try {
            if (defaultValue instanceof String) {
                return (T) sharedPreferences.getString(key, (String) defaultValue);
            } else if (defaultValue instanceof Integer) {
                return (T) (Integer) sharedPreferences.getInt(key, (Integer) defaultValue);
            } else if (defaultValue instanceof Boolean) {
                return (T) (Boolean) sharedPreferences.getBoolean(key, (Boolean) defaultValue);
            } else if (defaultValue instanceof Float) {
                return (T) (Float) sharedPreferences.getFloat(key, (Float) defaultValue);
            } else if (defaultValue instanceof Long) {
                return (T) (Long) sharedPreferences.getLong(key, (Long) defaultValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void setPreference(String key, T value) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            }
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearAllPreferences() {
        if (sharedPreferences != null)
            sharedPreferences.edit().clear().commit();
    }


    public static void clearAllPreferences(String[] keyToBeSaved) {
        if (sharedPreferences != null) {
            Map<String, Object> map = new ConcurrentHashMap<>(sharedPreferences.getAll());
            for (String stringObjectEntry : map.keySet()) {
                if (!Arrays.asList(keyToBeSaved).contains(stringObjectEntry)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(stringObjectEntry).commit();
                }
            }
        }
    }
}