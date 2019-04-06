package net.rickiekarp.shoppinghelper.communication;


import net.rickiekarp.shoppinghelper.communication.vo.VOCredentials;
import net.rickiekarp.shoppinghelper.communication.vo.VOData;
import net.rickiekarp.shoppinghelper.communication.vo.VONotes;
import net.rickiekarp.shoppinghelper.communication.vo.VOResult;
import net.rickiekarp.shoppinghelper.communication.vo.VOToken;

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
        @POST("account/authorize/")
        Call<VOToken> doGetToken(@Body VOCredentials credentials);


        @POST("account/login")                                         // Token String: "Basic token"
        Call<VOData> doLogin(@Header(Params.AUTHORIZATION) String token);

        @POST("account/create")
        Call<VOToken> doCreateAccount(@Body VOCredentials credentials);

    }

}