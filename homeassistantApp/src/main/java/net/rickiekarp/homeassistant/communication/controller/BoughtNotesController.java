package net.rickiekarp.homeassistant.communication.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.rickiekarp.homeassistant.communication.ApiInterfaces;
import net.rickiekarp.homeassistant.communication.vo.VONotes;
import net.rickiekarp.homeassistant.interfaces.IOnRemoveNoteResult;
import net.rickiekarp.homeassistant.interfaces.IRunController;
import net.rickiekarp.homeassistant.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.rickiekarp.homeassistant.Constants.URL.BASE_URL_APPSERVER;

/**
 * Created by sebastian on 22.11.17.
 */

public class BoughtNotesController implements Callback<VONotes>, IRunController {

    private String token;
    private IOnRemoveNoteResult uiCallback;
    private int id;
    private int noteId;

    public BoughtNotesController(String token, IOnRemoveNoteResult uiCallback, int id, int noteid) {
        this.token = token;
        this.uiCallback = uiCallback;
        this.id = id;
        this.noteId = noteid;
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

        VONotes vo = new VONotes(noteId);
        Call<VONotes> call = api.doMarkAsBought(Util.generateToken(token), vo);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<VONotes> call, Response<VONotes> response) {
        if (response.code() == 200) {
            uiCallback.onRemoveNoteSuccess(id);
        } else {
            uiCallback.onRemoveNoteError();
        }
    }

    @Override
    public void onFailure(Call<VONotes> call, Throwable t) {
        uiCallback.onRemoveNoteError();
        t.printStackTrace();
    }
}
