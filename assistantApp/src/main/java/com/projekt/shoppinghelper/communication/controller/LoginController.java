package com.projekt.shoppinghelper.communication.controller;

import android.content.SharedPreferences;

import com.projekt.shoppinghelper.utils.Util;
import com.projekt.shoppinghelper.communication.ApiInterfaces;
import com.projekt.shoppinghelper.communication.vo.VOData;
import com.projekt.shoppinghelper.db.AppDatabase;
import com.projekt.shoppinghelper.interfaces.IOnLoginResult;
import com.projekt.shoppinghelper.preferences.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.projekt.shoppinghelper.Constants.URL.BASE_URL_LOGIN;

/**
 * Created by sebastian on 17.11.17.
 */

public class LoginController implements Callback<VOData> {

    private SharedPreferences sp;
    private IOnLoginResult uiCallback;
    private AppDatabase database;

    public LoginController(SharedPreferences sp, IOnLoginResult uiCallback, AppDatabase database) {
        this.sp = sp;
        this.uiCallback = uiCallback;
        this.database = database;
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterfaces.LoginApi api = retrofit.create(ApiInterfaces.LoginApi.class);

        String voToken = sp.getString(Token.KEY, "");
        Call<VOData> call = api.doLogin(Util.generateToken(voToken));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<VOData> call, Response<VOData> response) {
        // -> save login to preferences
        if (response.code() == 200) {
            VOData voData = response.body();
            database.setAppData(voData);

            uiCallback.onLoginSuccess();
        } else {
            uiCallback.onLoginError();
        }
    }


    @Override
    public void onFailure(Call<VOData> call, Throwable t) {
        t.printStackTrace();
        uiCallback.onLoginError();
    }
}
