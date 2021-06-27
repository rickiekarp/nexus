package com.example.sebastian.projektapp.communication.controller;

import android.content.SharedPreferences;

import com.example.sebastian.projektapp.communication.ApiInterfaces;
import com.example.sebastian.projektapp.communication.vo.VONotes;
import com.example.sebastian.projektapp.db.AppDatabase;
import com.example.sebastian.projektapp.db.entities.Notes;
import com.example.sebastian.projektapp.interfaces.IOnAddNotesResult;
import com.example.sebastian.projektapp.interfaces.IRunController;
import com.example.sebastian.projektapp.preferences.Token;
import com.example.sebastian.projektapp.utils.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.sebastian.projektapp.Constants.URL.BASE_URL_APPSERVER;

/**
 * Created by sebastian on 22.11.17.
 */

public class AddNotesController implements Callback<VONotes>, IRunController {

    private SharedPreferences sp;
    private IOnAddNotesResult uiCallback;
    private String title, body;
    private AppDatabase database;

    public AddNotesController(SharedPreferences sp, IOnAddNotesResult uiCallback, String title, String body, AppDatabase database) {
        this.uiCallback = uiCallback;
        this.sp = sp;
        this.title = title;
        this.body = body;
        this.database = database;
    }

    @Override
    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_APPSERVER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterfaces.NotesApi api = retrofit.create(ApiInterfaces.NotesApi.class);

        VONotes vo = new VONotes(title, body);
        Call<VONotes> call = api.doAddNotes(Util.generateToken(sp.getString(Token.KEY, "")), vo);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<VONotes> call, Response<VONotes> response) {
        if (response.code() == 200) {
            VONotes vo = response.body();
            if (vo != null) {

                database.notesDAO().insert(createNote(vo));
                uiCallback.onAddNotesSuccess(vo.getTitle(), vo.getContent());
            }
        } else {
            uiCallback.onAddNotesError();
        }
    }

    @Override
    public void onFailure(Call<VONotes> call, Throwable t) {
        uiCallback.onAddNotesError();
        t.printStackTrace();
    }

    private Notes createNote(VONotes vo) {
        Notes note = new Notes();
        note.id = vo.getNoteId();
        note.title = vo.getTitle();
        note.content = vo.getContent();

        return note;
    }
}
