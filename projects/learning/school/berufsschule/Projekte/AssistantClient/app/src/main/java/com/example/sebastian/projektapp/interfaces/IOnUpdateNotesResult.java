package com.example.sebastian.projektapp.interfaces;

import com.example.sebastian.projektapp.db.entities.Notes;

/**
 * Created by sebastian on 06.12.17.
 */

public interface IOnUpdateNotesResult {

    void onUpdateNotesSuccess(String title, String body);
    void onUpdateNotesError();
}
