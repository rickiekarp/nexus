package com.example.sebastian.projektapp.communication;


import com.example.sebastian.projektapp.communication.vo.VOCredentials;
import com.example.sebastian.projektapp.communication.vo.VOData;
import com.example.sebastian.projektapp.communication.vo.VONotes;
import com.example.sebastian.projektapp.communication.vo.VOResult;
import com.example.sebastian.projektapp.communication.vo.VOToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by sebastian on 10.11.17.
 */

public abstract class ApiInterfaces {

    private static class Params {
        static final String AUTHORIZATION = "Authorization";

        static final String NOTE_DOMAIN = "note";
    }

    public interface NotesApi {

        @GET("note/getAll/")
        Call<List<VONotes>> getNotes(@Header(Params.AUTHORIZATION) String apiToken);


        @POST("note/add/")
        Call<VONotes> doAddNotes(@Header(Params.AUTHORIZATION) String apiToken, @Body VONotes notes);

        @POST("note/update/")
        Call<VONotes> doUpdateNotes(@Header(Params.AUTHORIZATION) String apiToken, @Body VONotes notes);

        @POST("note/remove/")
        Call<VOResult> doRemoveNotes(@Header(Params.AUTHORIZATION) String apiToken, @Body VONotes notes);
    }

    public interface LoginApi {
        @POST("auth/token/")
        Call<VOToken> doGetToken(@Body VOCredentials credentials);


        @POST("account/login")                                         // Token String: "Basic token"
        Call<VOData> doLogin(@Header(Params.AUTHORIZATION) String token);

        @POST("account/create")
        Call<VOToken> doCreateAccount(@Body VOCredentials credentials);

    }

}