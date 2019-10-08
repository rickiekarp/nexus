package net.rickiekarp.core.account;

import net.rickiekarp.core.debug.LogFileHandler;

public class Account {
    private String user;
    private String password;
    private String accessToken;

    public Account(final String user, final String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    void setAccessToken(String accessToken) {
        LogFileHandler.logger.info("Setting new access token!");
        this.accessToken = accessToken;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
