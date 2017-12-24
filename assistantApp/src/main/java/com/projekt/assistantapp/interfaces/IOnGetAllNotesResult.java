package com.projekt.assistantapp.interfaces;

import com.projekt.assistantapp.communication.vo.VONotes;
import com.projekt.assistantapp.provider.Notes;

import java.util.List;

/**
 * Created by sebastian on 05.12.17.
 */

public interface IOnGetAllNotesResult {
    void onGetAllNotesSuccess(List<VONotes> notesList);
    void onGetAllNotesError();
}
