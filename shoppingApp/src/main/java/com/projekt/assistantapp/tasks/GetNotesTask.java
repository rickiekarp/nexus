package com.projekt.assistantapp.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.projekt.assistantapp.communication.controller.GetNotesController;
import com.projekt.assistantapp.interfaces.IOnGetAllNotesResult;

/**
 * Created by sebastian on 05.12.17.
 */

public class GetNotesTask extends AsyncTask<Void, Void, String> {

    private SharedPreferences sp;
    private IOnGetAllNotesResult uiCallback;

    public GetNotesTask(SharedPreferences sp, IOnGetAllNotesResult uiCallback) {
        this.sp = sp;
        this.uiCallback = uiCallback;
    }
    @Override
    protected String doInBackground(Void... voids) {
        GetNotesController getNotesController = new GetNotesController(sp, uiCallback);
        getNotesController.start();
        return "Task executed";
    }
}
