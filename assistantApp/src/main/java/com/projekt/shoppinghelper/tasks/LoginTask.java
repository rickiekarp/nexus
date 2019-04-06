package com.projekt.shoppinghelper.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.projekt.shoppinghelper.communication.controller.LoginController;
import com.projekt.shoppinghelper.db.AppDatabase;
import com.projekt.shoppinghelper.interfaces.IOnLoginResult;

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

