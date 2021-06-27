package com.example.sebastian.projektapp.interfaces;

import com.example.sebastian.projektapp.communication.vo.VONotes;
import com.example.sebastian.projektapp.provider.Notes;

import java.util.List;

/**
 * Created by sebastian on 05.12.17.
 */

public interface IOnGetAllNotesResult {
    void onGetAllNotesSuccess(List<VONotes> notesList);
    void onGetAllNotesError();
}
