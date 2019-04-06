package com.projekt.assistantapp.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.projekt.assistantapp.communication.controller.TokenController;
import com.projekt.assistantapp.interfaces.IOnGetTokenResult;

/**
 * Created by sebastian on 05.12.17.
 */

public class TokenTask extends AsyncTask<Void, Void, String> {

    SharedPreferences sp;
    IOnGetTokenResult uiCallback;
    String username, password;

    public TokenTask(SharedPreferences sp, IOnGetTokenResult uiCallback, String username, String password) {
        this.sp = sp;
        this.uiCallback = uiCallback;
        this.username = username;
        this.password = password;
    }
    @Override
    protected String doInBackground(Void... voids) {
        TokenController tokenController = new TokenController(sp, uiCallback, username, password);
        tokenController.start();

        return "Task finished";
    }
}
