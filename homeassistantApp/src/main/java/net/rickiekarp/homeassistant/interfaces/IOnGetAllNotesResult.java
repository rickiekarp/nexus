package net.rickiekarp.homeassistant.interfaces;

import net.rickiekarp.homeassistant.communication.vo.VONotes;

import java.util.List;

/**
 * Created by sebastian on 05.12.17.
 */

public interface IOnGetAllNotesResult {
    void onGetAllNotesSuccess(List<VONotes> notesList);
    void onGetAllNotesError();
}
