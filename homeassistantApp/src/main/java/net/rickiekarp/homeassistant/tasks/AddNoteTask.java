package net.rickiekarp.homeassistant.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import net.rickiekarp.homeassistant.communication.controller.AddNotesController;
import net.rickiekarp.homeassistant.db.AppDatabase;
import net.rickiekarp.homeassistant.interfaces.IOnAddNotesResult;

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
