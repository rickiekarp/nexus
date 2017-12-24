package com.projekt.assistantapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.projekt.assistantapp.MainActivity;
import com.projekt.assistantapp.preferences.IsLoggedIn;
import com.projekt.assistantapp.preferences.Token;

/**
 * Created by sebastian on 15.11.17.
 */

public class Util {



    private final String TAG = getClass().getSimpleName();

    private static Util instance;

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }

        return instance;
    }

    private Util() {

    }

    public static boolean isEmpty(String s) {
        if (s == null) {
            return true;
        } else if (s.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String generateToken(String voToken) {
        return "Basic " + voToken;
    }

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void setIsLoggedIn(SharedPreferences sp, boolean isLoggedIn) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IsLoggedIn.KEY, isLoggedIn);
        editor.commit();
    }

    public static void removeToken(SharedPreferences sp) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Token.KEY);
        editor.commit();
    }
}
