package com.group2.androidbankingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.group2.androidbankingapp.R;

public class SessionManager {
    private static final String USER_TOKEN = "USER_TOKEN";
    Context context;

    private SharedPreferences prefs;

    public  SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String fetchAuthToken() {
        return prefs.getString(USER_TOKEN, null);
    }
}
