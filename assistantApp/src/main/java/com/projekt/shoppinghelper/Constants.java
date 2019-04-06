package com.projekt.shoppinghelper;

import com.projekt.shoppinghelper.config.Configuration;

/**
 * Created by sebastian on 08.11.17.
 */
public abstract class Constants {
    public static final int HTTP_TIMEOUT = 20000;

    public static final String LOGIN_SERVER = "loginServer";

    public abstract class Preferences {
        public static final String SHARED_PREFERENCES_NAME = "sharedPreferencesName";
        public static final String PREF_USERNAME = "userName";
    }

    public abstract class Database {
        public static final String NAME = "appdb_data";
    }

    public static abstract class URL {
        public static final String BASE_URL_LOGIN = "http://" + Configuration.host + ":8080/LoginServer/";
        public static final String BASE_URL_APPSERVER = "http://" + Configuration.host + ":8080/AssistantServer/";
    }
}
