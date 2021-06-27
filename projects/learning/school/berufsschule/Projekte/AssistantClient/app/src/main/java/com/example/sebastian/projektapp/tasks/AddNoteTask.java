package com.example.sebastian.projektapp.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.sebastian.projektapp.communication.controller.AddNotesController;
import com.example.sebastian.projektapp.db.AppDatabase;
import com.example.sebastian.projektapp.interfaces.IOnAddNotesResult;

/**
 * Created by sebastian on 06.12.17.
 */

public class AddNoteTask extends AsyncTask<Void, Void, String> {

    private SharedPreferences sp;
    private IOnAddNotesResult uiCallback;
    private String title, body;
    private AppDatabase database;

    public AddNoteTask (SharedPreferences sp, IOnAddNotesResult uiCallback, String title, String body, AppDatabase database) {
        this.sp = sp;
        this.uiCallback = uiCallback;
        this.title = title;
        this.body = body;
        this.database = database;
    }

    @Override
    protected String doInBackground(Void... voids) {

        AddNotesController addNotesController = new AddNotesController(sp, uiCallback, title, body, database);
        addNotesController.start();

        return "Task executed";
    }
}
