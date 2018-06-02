package com.rkarp.botlib.model;

public class Credentials {
    private final String login;
    private final String password;

    public Credentials(String user, String pass) {
        login = user;
        password = pass;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
