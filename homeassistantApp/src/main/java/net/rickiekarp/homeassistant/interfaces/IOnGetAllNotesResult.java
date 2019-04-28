package net.rickiekarp.homeassistant.interfaces;

import net.rickiekarp.homeassistant.communication.vo.VONote;

import java.util.List;

/**
 * Created by sebastian on 05.12.17.
 */

public interface IOnGetAllNotesResult {
    void onGetAllNotesSuccess(List<VONote> notesList);
    void onGetAllNotesError();
}
