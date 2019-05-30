package net.rickiekarp.homeassistant.tasks.notes;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import net.rickiekarp.homeassistant.communication.controller.GetNotesController;
import net.rickiekarp.homeassistant.communication.controller.GetNotesHistoryController;
import net.rickiekarp.homeassistant.interfaces.IOnGetAllNotesResult;

/**
 * Created by sebastian on 05.12.17.
 */

public class GetNotesHistoryTask extends AsyncTask<Void, Void, String> {

    private SharedPreferences sp;
    private IOnGetAllNotesResult uiCallback;

    public GetNotesHistoryTask(SharedPreferences sp, IOnGetAllNotesResult uiCallback) {
        this.sp = sp;
        this.uiCallback = uiCallback;
    }
    @Override
    protected String doInBackground(Void... voids) {
        GetNotesHistoryController getNotesController = new GetNotesHistoryController(sp, uiCallback);
        getNotesController.start();
        return "Task executed";
    }
}
