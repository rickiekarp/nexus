package com.projekt.assistantapp.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.projekt.assistantapp.communication.controller.CreateAccountController;
import com.projekt.assistantapp.interfaces.IOnCreateAccountResult;

/**
 * Created by sebastian on 04.12.17.
 */

public class RegistrationTask extends AsyncTask<Void, Void, String> {

    private SharedPreferences sp;
    private String username, password;
    private IOnCreateAccountResult uiCallback;

    public RegistrationTask(SharedPreferences sp, String username, String password, IOnCreateAccountResult uiCallback) {
        this.sp = sp;
        this.username = username;
        this.password = password;
        this.uiCallback = uiCallback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        CreateAccountController createAccountController = new CreateAccountController(sp, username, password, uiCallback);
        createAccountController.start();

        return "Task Finished";
    }
}