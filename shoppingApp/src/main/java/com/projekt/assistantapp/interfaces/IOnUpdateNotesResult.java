package com.projekt.assistantapp.interfaces;

import com.projekt.assistantapp.db.entities.Notes;

/**
 * Created by sebastian on 06.12.17.
 */

public interface IOnUpdateNotesResult {

    void onUpdateNotesSuccess(String title, String body);
    void onUpdateNotesError();
}
