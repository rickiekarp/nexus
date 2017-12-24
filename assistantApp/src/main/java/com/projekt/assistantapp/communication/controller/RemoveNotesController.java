package com.projekt.assistantapp.communication.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.projekt.assistantapp.communication.ApiInterfaces;
import com.projekt.assistantapp.communication.vo.VONotes;
import com.projekt.assistantapp.communication.vo.VOResult;
import com.projekt.assistantapp.db.AppDatabase;
import com.projekt.assistantapp.interfaces.IOnRemoveNoteResult;
import com.projekt.assistantapp.interfaces.IRunController;
import com.projekt.assistantapp.preferences.Token;
import com.projekt.assistantapp.utils.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.projekt.assistantapp.Constants.URL.BASE_URL_APPSERVER;

/**
 * Created by sebastian on 22.11.17.
 */

public class RemoveNotesController implements Callback<VOResult>, IRunController {

    private AppDatabase database;
    private IOnRemoveNoteResult uiCallback;
    private SharedPreferences sp;
    private int id;

    public RemoveNotesController(SharedPreferences sp, IOnRemoveNoteResult uiCallback, AppDatabase database, int id) {
        this.uiCallback = uiCallback;
        this.sp = sp;
        this.database = database;
        this.id = id;
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

        VONotes vo = new VONotes(id);
        Call<VOResult> call = api.doRemoveNotes(Util.generateToken(sp.getString(Token.KEY, "")), vo);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<VOResult> call, Response<VOResult> response) {

        if (response.code() == 200) {
            uiCallback.onRemoveNoteSuccess(id);
        } else {
            uiCallback.onRemoveNoteError();
        }
    }

    @Override
    public void onFailure(Call<VOResult> call, Throwable t) {
        uiCallback.onRemoveNoteError();
        t.printStackTrace();
    }
}
