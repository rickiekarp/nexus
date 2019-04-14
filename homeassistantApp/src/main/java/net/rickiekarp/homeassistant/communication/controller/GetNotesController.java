package net.rickiekarp.homeassistant.communication.controller;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.rickiekarp.homeassistant.communication.ApiInterfaces;
import net.rickiekarp.homeassistant.communication.vo.VONotes;
import net.rickiekarp.homeassistant.interfaces.IOnGetAllNotesResult;
import net.rickiekarp.homeassistant.interfaces.IRunController;
import net.rickiekarp.homeassistant.preferences.Token;
import net.rickiekarp.homeassistant.utils.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.rickiekarp.homeassistant.Constants.URL.BASE_URL_APPSERVER;

/**
 * Created by sebastian on 22.11.17.
 */

public class GetNotesController implements Callback<List<VONotes>>, IRunController {

    private SharedPreferences sp;
    private IOnGetAllNotesResult uiCallback;

    public GetNotesController(SharedPreferences sp, IOnGetAllNotesResult uiCallback) {
        this.uiCallback = uiCallback;
        this.sp = sp;
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

        Call<List<VONotes>> call = api.getNotes(Util.generateToken(sp.getString(Token.KEY, "")));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<VONotes>> call, Response<List<VONotes>> response) {
        if (response.code() == 200) {
            List<VONotes> voList = response.body();
            uiCallback.onGetAllNotesSuccess(voList);
        } else {
            uiCallback.onGetAllNotesError();
        }
    }

    @Override
    public void onFailure(Call<List<VONotes>> call, Throwable t) {
        uiCallback.onGetAllNotesError();
    }
}
