package com.example.storee;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("AppKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setLogin (boolean login) {
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    public boolean getLogin() {
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    public void setUserId (int userId) {
        editor.putInt("KEY_USER_ID", userId);
        editor.commit();
    }

    public int getUserId() {
        return sharedPreferences.getInt("KEY_USER_ID", -1);
    }
}
