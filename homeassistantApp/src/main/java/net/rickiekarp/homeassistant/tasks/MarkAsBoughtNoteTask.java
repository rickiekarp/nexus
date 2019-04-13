package net.rickiekarp.homeassistant.tasks;

import android.os.AsyncTask;

import net.rickiekarp.homeassistant.communication.controller.BoughtNotesController;
import net.rickiekarp.homeassistant.interfaces.IOnRemoveNoteResult;

/**
 * Created by sebastian on 06.12.17.
 */

public class MarkAsBoughtNoteTask extends AsyncTask<Void, Void, String> {

    String sp;
    int id;
    int noteId;
    IOnRemoveNoteResult uiCallback;

    public MarkAsBoughtNoteTask(String sp, IOnRemoveNoteResult uiCallback, int id, int noteid) {
        this.sp = sp;
        this.id = id;
        this.noteId = noteid;
        this.uiCallback = uiCallback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        BoughtNotesController updateNotesController = new BoughtNotesController(sp, uiCallback, id, noteId);
        updateNotesController.start();
        return "Task executed";
    }
}
