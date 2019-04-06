package net.rickiekarp.shoppinghelper.interfaces;

/**
 * Created by sebastian on 06.12.17.
 */

public interface IOnUpdateNotesResult {

    void onUpdateNotesSuccess(String title, String body);
    void onUpdateNotesError();
}
