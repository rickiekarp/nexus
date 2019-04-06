package net.rickiekarp.shoppinghelper.communication.controller;

import android.content.SharedPreferences;

import net.rickiekarp.shoppinghelper.communication.ApiInterfaces;
import net.rickiekarp.shoppinghelper.communication.vo.VONotes;
import net.rickiekarp.shoppinghelper.db.AppDatabase;
import net.rickiekarp.shoppinghelper.interfaces.IOnUpdateNotesResult;
import net.rickiekarp.shoppinghelper.interfaces.IRunController;
import net.rickiekarp.shoppinghelper.preferences.Token;
import net.rickiekarp.shoppinghelper.utils.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.rickiekarp.shoppinghelper.Constants.URL.BASE_URL_APPSERVER;

/**
 * Created by sebastian on 22.11.17.
 */

public class UpdateNotesController implements Callback<VONotes>, IRunController {

    private SharedPreferences sp;
    private String title, body;
    private AppDatabase database;
    private IOnUpdateNotesResult uiCallback;

    public UpdateNotesController(SharedPreferences sp, IOnUpdateNotesResult uiCallback, String title, String body, AppDatabase database) {
        this.sp = sp;
        this.uiCallback = uiCallback;
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
        Call<VONotes> call = api.doUpdateNotes(Util.generateToken(sp.getString(Token.KEY, "")), vo);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<VONotes> call, Response<VONotes> response) {
        VONotes vo = response.body();
        if (response.code() == 200) {

            if (vo != null) {
                uiCallback.onUpdateNotesSuccess(vo.getTitle(), vo.getContent());
            }
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
