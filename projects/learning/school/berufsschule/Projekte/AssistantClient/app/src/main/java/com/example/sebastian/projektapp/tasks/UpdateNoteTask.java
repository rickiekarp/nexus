package com.example.sebastian.projektapp.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.sebastian.projektapp.communication.controller.UpdateNotesController;
import com.example.sebastian.projektapp.db.AppDatabase;
import com.example.sebastian.projektapp.interfaces.IOnUpdateNotesResult;

/**
 * Created by sebastian on 06.12.17.
 */

public class UpdateNoteTask extends AsyncTask<Void, Void, String> {

    SharedPreferences sp;
    String title, body;
    AppDatabase database;
    IOnUpdateNotesResult uiCallback;

    public UpdateNoteTask(SharedPreferences sp, IOnUpdateNotesResult uiCallback, String title, String body, AppDatabase database) {
        this.sp = sp;
        this.title = title;
        this.body = body;
        this.database = database;
        this.uiCallback = uiCallback;
    }
    @Override
    protected String doInBackground(Void... voids) {

        UpdateNotesController updateNotesController = new UpdateNotesController(sp, uiCallback, title, body, database);
        updateNotesController.start();
        return "Task executed";
    }
}
