package com.projekt.assistantapp.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.projekt.assistantapp.communication.controller.UpdateNotesController;
import com.projekt.assistantapp.db.AppDatabase;
import com.projekt.assistantapp.interfaces.IOnUpdateNotesResult;

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
