package net.rickiekarp.homeassistant.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import net.rickiekarp.homeassistant.communication.controller.UpdateNotesController;
import net.rickiekarp.homeassistant.db.AppDatabase;
import net.rickiekarp.homeassistant.interfaces.IOnUpdateNotesResult;

/**
 * Created by sebastian on 06.12.17.
 */

public class UpdateNoteTask extends AsyncTask<Void, Void, String> {

    String token;
    String title;
    AppDatabase database;
    IOnUpdateNotesResult uiCallback;

    public UpdateNoteTask(String token, IOnUpdateNotesResult uiCallback, String title, AppDatabase database) {
        this.token = token;
        this.title = title;
        this.database = database;
        this.uiCallback = uiCallback;
    }
    @Override
    protected String doInBackground(Void... voids) {

        UpdateNotesController updateNotesController = new UpdateNotesController(token, uiCallback, title, database);
        updateNotesController.start();
        return "Task executed";
    }
}
