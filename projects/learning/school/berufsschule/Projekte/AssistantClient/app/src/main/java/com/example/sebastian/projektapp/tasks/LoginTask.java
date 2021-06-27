package com.example.sebastian.projektapp.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.sebastian.projektapp.communication.controller.LoginController;
import com.example.sebastian.projektapp.db.AppDatabase;
import com.example.sebastian.projektapp.interfaces.IOnLoginResult;

/**
 * Created by sebastian on 04.12.17.
 */
public class LoginTask extends AsyncTask<Void, Void, String> {

    private SharedPreferences sp;
    private IOnLoginResult uiCallback;
    private AppDatabase database;

    public LoginTask(SharedPreferences sp, IOnLoginResult uiCallback, AppDatabase database) {
        this.uiCallback = uiCallback;
        this.sp = sp;
        this.database = database;
    }

    @Override
    protected String doInBackground(Void... voids) {
        LoginController loginController = new LoginController(sp, uiCallback, database);
        loginController.start();

        return "Task finished";
    }

}

