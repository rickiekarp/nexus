package net.rickiekarp.shoppinghelper.interfaces;

import net.rickiekarp.shoppinghelper.communication.vo.VONotes;

import java.util.List;

/**
 * Created by sebastian on 05.12.17.
 */

public interface IOnGetAllNotesResult {
    void onGetAllNotesSuccess(List<VONotes> notesList);
    void onGetAllNotesError();
}
