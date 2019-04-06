package net.rickiekarp.shoppinghelper.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import net.rickiekarp.shoppinghelper.communication.controller.GetNotesController;
import net.rickiekarp.shoppinghelper.interfaces.IOnGetAllNotesResult;

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
