package net.rickiekarp.homeassistant.communication.controller;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.rickiekarp.homeassistant.communication.ApiInterfaces;
import net.rickiekarp.homeassistant.communication.vo.VONotes;
import net.rickiekarp.homeassistant.config.Configuration;
import net.rickiekarp.homeassistant.db.AppDatabase;
import net.rickiekarp.homeassistant.db.entities.Notes;
import net.rickiekarp.homeassistant.interfaces.IOnAddNotesResult;
import net.rickiekarp.homeassistant.interfaces.IRunController;
import net.rickiekarp.homeassistant.preferences.Token;
import net.rickiekarp.homeassistant.utils.Util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.rickiekarp.homeassistant.Constants.URL.BASE_URL_APPSERVER;

/**
 * Created by sebastian on 22.11.17.
 */

public class AddNotesController implements Callback<VONotes>, IRunController {

    private SharedPreferences sp;
    private IOnAddNotesResult uiCallback;
    private String title;
    private AppDatabase database;

    public AddNotesController(SharedPreferences sp, IOnAddNotesResult uiCallback, String title, AppDatabase database) {
        this.uiCallback = uiCallback;
        this.sp = sp;
        this.title = title;
        this.database = database;
    }

    @Override
    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.host + BASE_URL_APPSERVER)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterfaces.NotesApi api = retrofit.create(ApiInterfaces.NotesApi.class);

        VONotes vo = new VONotes(title);
        Call<VONotes> call = api.doAddNotes(Util.generateToken(sp.getString(Token.KEY, "")), vo);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<VONotes> call, Response<VONotes> response) {
        if (response.code() == 200) {
            VONotes vo = response.body();
            if (vo != null) {

                database.notesDAO().insert(createNote(vo));
                uiCallback.onAddNotesSuccess(vo.getTitle());
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
        note.id = vo.getId();
        note.title = vo.getTitle();

        return note;
    }
}
