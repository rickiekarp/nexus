package net.rickiekarp.homeassistant.communication.controller;

import android.content.SharedPreferences;

import net.rickiekarp.homeassistant.communication.ApiInterfaces;
import net.rickiekarp.homeassistant.communication.vo.VONotes;
import net.rickiekarp.homeassistant.db.AppDatabase;
import net.rickiekarp.homeassistant.interfaces.IOnUpdateNotesResult;
import net.rickiekarp.homeassistant.interfaces.IRunController;
import net.rickiekarp.homeassistant.preferences.Token;
import net.rickiekarp.homeassistant.utils.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.rickiekarp.homeassistant.Constants.URL.BASE_URL_APPSERVER;

/**
 * Created by sebastian on 22.11.17.
 */

public class UpdateNotesController implements Callback<VONotes>, IRunController {

    private String sp;
    private String title;
    private AppDatabase database;
    private IOnUpdateNotesResult uiCallback;

    public UpdateNotesController(String sp, IOnUpdateNotesResult uiCallback, String title, AppDatabase database) {
        this.sp = sp;
        this.uiCallback = uiCallback;
        this.title = title;
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

        VONotes vo = new VONotes(title);
        Call<VONotes> call = api.doUpdateNotes(Util.generateToken(sp), vo);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<VONotes> call, Response<VONotes> response) {
        if (response.code() == 200) {
            uiCallback.onUpdateNotesSuccess(title);
        } else {
            uiCallback.onUpdateNotesError();
        }
    }

    @Override
    public void onFailure(Call<VONotes> call, Throwable t) {
        uiCallback.onUpdateNotesError();
        t.printStackTrace();
    }
}
