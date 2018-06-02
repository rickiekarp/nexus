package com.rkarp.appcore;

import com.rkarp.appcore.account.AccountManager;
import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.net.NetworkApi;
import com.rkarp.appcore.settings.Configuration;
import com.rkarp.appcore.util.FileUtil;

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class AppContext {
    private static AppContext appContext;
    private NetworkApi appNetworkApi;
    private AccountManager accountManager;
    private String contextIdentifier;
    private String internalVersion;

    protected AppContext(String identifier, NetworkApi appNetwork) {
        contextIdentifier = identifier;
        appNetworkApi = appNetwork;
    }

    public static void create(String identifier) {
        appContext = new AppContext(identifier, new NetworkApi());
    }

    public static void create(String identifier, NetworkApi network) {
        appContext = new AppContext(identifier, network);
    }

    public static AppContext getContext() {
        return appContext;
    }

    public void initAccountManager() {
        accountManager = new AccountManager();
    }

    public String getApplicationName() {
        return LanguageController.getString(contextIdentifier);
    }

    public String getInternalVersion() {
        return internalVersion;
    }

    public String getContextIdentifier() {
        return contextIdentifier;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public NetworkApi getNetworkApi() {
        return appNetworkApi;
    }

    public String getVersionNumber() {
        Manifest manifest;
        try {
            manifest = new JarFile(Configuration.config.getJarFile().getPath()).getManifest();
            return FileUtil.readManifestProperty(manifest, "Version");
        } catch (IOException e) {
            return internalVersion;
        }
    }

    public void setInternalVersion(String version) {
        internalVersion = version;
    }
}
