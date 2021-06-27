package com.example.sebastian.projektapp.interfaces;

/**
 * Created by sebastian on 06.12.17.
 */

public interface IOnAddNotesResult {

    void onAddNotesSuccess(String title, String body);
    void onAddNotesError();
}
