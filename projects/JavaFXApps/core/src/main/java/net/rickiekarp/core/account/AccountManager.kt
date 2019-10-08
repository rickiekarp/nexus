package net.rickiekarp.core.account;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.net.NetResponse;
import net.rickiekarp.core.net.NetworkAction;
import net.rickiekarp.core.net.NetworkApi;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.util.FileUtil;
import net.rickiekarp.core.util.crypt.Base64Coder;
import net.rickiekarp.core.util.parser.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class AccountManager {
    private Account account;
    private boolean rememberPass;
    private boolean autoLogin;

    private final String PROFILE_KEY = "profile.json";

    public AccountManager() {
            this.account = loadAccountFromFile();
            LogFileHandler.logger.config("ACCOUNT=" + account);
    }

    private Account loadAccountFromFile() {
        File activeProfile = new File(Configuration.config.getProfileDirFile() + "/active");
        if (activeProfile.exists()) {
            try {
                String profileString = FileUtil.readFirstLineFromFile(activeProfile);
                activeProfile = new File(Configuration.config.getProfileDirFile() + "/" + profileString + "/" + PROFILE_KEY);
                JSONObject profileJson = JsonParser.readJsonFromFile(activeProfile);
                if (profileJson == null) {
                    return null;
                }
                String password;
                try {
                    password = Base64Coder.decodeString(profileJson.getString("signinkey"));
                    rememberPass = true;
                } catch (JSONException e) {
                    password = "";
                }
                autoLogin = profileJson.getBoolean("autoLogin");
                return new Account(profileString, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogFileHandler.logger.warning("File: " + activeProfile.getPath() + " not found!");
        }
        return null;
    }

    public void createActiveProfile(boolean rememberPass, boolean autoLogin) {
        File profileDir = new File(Configuration.config.getProfileDirFile().getPath() + "/" + account.getUser());
        if (profileDir.mkdirs()) {
            LogFileHandler.logger.log(Level.INFO, "created " + profileDir.getPath());
        }

        JSONObject useraccount = new JSONObject();
        if (rememberPass) {
            useraccount.put("signinkey", Base64Coder.encodeString(account.getPassword()));
        }
        useraccount.put("autoLogin", autoLogin);
        JsonParser.writeJsonObjectToFile(useraccount, profileDir, PROFILE_KEY);

        profileDir = new File(Configuration.config.getProfileDirFile() + "/active");
        byte[] activeProfileData = account.getUser().getBytes();
        FileUtil.writeFile(activeProfileData, profileDir.getPath());
    }

    public boolean updateAccessToken() {
        NetworkAction validationAction = NetworkApi.requestAccessToken(account);
        InputStream inputStream = AppContext.getContext().getNetworkApi().runNetworkAction(validationAction);
        if (inputStream != null) {
            JSONObject token = NetResponse.getResponseJson(inputStream);
            if (token != null) {
                account.setAccessToken(token.getString("token"));
                return true;
            }
        }
        return false;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isRememberPass() {
        return rememberPass;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
