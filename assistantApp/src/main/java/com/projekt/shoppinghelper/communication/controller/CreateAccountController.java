package com.projekt.shoppinghelper.communication.controller;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.projekt.shoppinghelper.communication.ApiInterfaces;
import com.projekt.shoppinghelper.communication.vo.VOCredentials;
import com.projekt.shoppinghelper.communication.vo.VOToken;
import com.projekt.shoppinghelper.interfaces.IOnCreateAccountResult;
import com.projekt.shoppinghelper.interfaces.IRunController;
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

public class CreateAccountController implements Callback<VOToken>, IRunController {

    private SharedPreferences sp;
    private String username, password;
    private IOnCreateAccountResult taskCallback;

    public CreateAccountController(SharedPreferences sp, String username, String password, IOnCreateAccountResult taskCallback) {
        this.sp = sp;
        this.username = username;
        this.password = password;
        this.taskCallback = taskCallback;
    }

    @Override
    public void start() {
        VOCredentials credentials = new VOCredentials(username, password);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterfaces.LoginApi api = retrofit.create(ApiInterfaces.LoginApi.class);

        Call<VOToken> call = api.doCreateAccount(credentials);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<VOToken> call, @NonNull Response<VOToken> response) {
        if (response.code() == 200) {
            VOToken vo = response.body();
            if (vo != null) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Token.KEY, vo.getToken());
                editor.apply();

                taskCallback.onCreationSuccess();

            }
        } else {
            taskCallback.onCreationError();
        }
    }

    @Override
    public void onFailure(@NonNull Call<VOToken> call, @NonNull Throwable t) {
        t.printStackTrace();
        taskCallback.onCreationError();
    }
}
