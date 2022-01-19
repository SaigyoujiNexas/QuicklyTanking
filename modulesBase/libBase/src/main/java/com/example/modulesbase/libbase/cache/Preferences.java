package com.example.modulesbase.libbase.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

public class Preferences {
    private static SharedPreferences preferences;
    static void init(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static boolean getBoolean(String key, boolean defValue)
    {
        return preferences.getBoolean(key, defValue);
    }
    public static void saveBoolean(String key, boolean value)
    {
        preferences.edit().putBoolean(key, value).apply();
    }
    public static int getInt(String key, int defValue){
        return preferences.getInt(key, defValue);
    }
    public static void saveInt(String key, int value)
    {
        preferences.edit().putInt(key, value).apply();
    }
    public static long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public static void saveLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public static String getString(String key, @Nullable String defValue) {
        return preferences.getString(key, defValue);
    }

    public static void saveString(String key, @Nullable String value) {
        preferences.edit().putString(key, value).apply();
    }

    public static void remove(String key) {
        preferences.edit().remove(key).apply();
    }


}
