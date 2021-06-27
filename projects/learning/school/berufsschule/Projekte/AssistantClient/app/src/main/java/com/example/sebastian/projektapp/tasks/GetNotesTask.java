package com.example.sebastian.projektapp.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.sebastian.projektapp.communication.controller.GetNotesController;
import com.example.sebastian.projektapp.interfaces.IOnGetAllNotesResult;

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
