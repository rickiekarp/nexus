package com.rkarp.botlib.model;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.botlib.enums.BotPlatforms;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PluginData {
    public static ObservableList<PluginData> pluginData = FXCollections.observableArrayList();

    private final SimpleStringProperty pluginClazz;
    private final SimpleStringProperty pluginName;
    private final SimpleStringProperty pluginOldVersion;
    private final SimpleStringProperty pluginNewVersion;
    private BotPlatforms pluginType;
    private final SimpleBooleanProperty pluginUpdateEnable;
    private final SimpleStringProperty pluginPackage;
    private final SimpleStringProperty pluginActvity;
    private Credentials pluginCredentials;

    public PluginData(String pluginClazz, String pluginName, String pluginOldVersion, String pluginNewVersion, BotPlatforms platform) {
        this.pluginClazz = new SimpleStringProperty(pluginClazz);
        this.pluginName = new SimpleStringProperty(pluginName);
        this.pluginOldVersion = new SimpleStringProperty(pluginOldVersion);
        this.pluginNewVersion = new SimpleStringProperty(pluginNewVersion);
        this.pluginType = platform;
        this.pluginUpdateEnable = new SimpleBooleanProperty();
        this.pluginPackage = new SimpleStringProperty(); //android only
        this.pluginActvity = new SimpleStringProperty(); //android only
    }

    public String getPluginClazz() {
        return pluginClazz.get();
    }
    public void setPluginClazz(String clazz) {
        this.pluginClazz.set(clazz);
    }

    public String getPluginName() {
        return pluginName.get();
    }

    public String getPluginOldVersion() {
        return pluginOldVersion.get();
    }
    public void setPluginOldVersion(String version) {
        this.pluginOldVersion.set(version);
    }

    public String getPluginNewVersion() {
        return pluginNewVersion.get();
    }
    public void setPluginNewVersion(String version) {
        this.pluginNewVersion.set(version);
    }


    public BotPlatforms getPluginType() {
        return pluginType;
    }
    public void setBotType(BotPlatforms type) {
        pluginType = type;
    }

    public boolean getUpdateEnable() {
        return pluginUpdateEnable.get();
    }
    public void setUpdateEnable(boolean enable) {
        pluginUpdateEnable.set(enable);
    }

    public String setNewEditButtonName() {
        //if local plugin does not exist, show download button
        if (pluginOldVersion.get() == null) {
            return LanguageController.getString("download");
        } else if (pluginNewVersion.get() == null) {
            //show nothing

            //if remote version is newer than the local one
        } else {
            try {
                if (Integer.parseInt(pluginNewVersion.get().replace(".", "")) > Integer.parseInt(pluginOldVersion.get().replace(".", ""))) {
                    return LanguageController.getString("update");
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public String getPluginPackage() {
        return pluginPackage.get();
    }
    public void setPluginPackage(String name) {
        pluginPackage.set(name);
    }

    public String getPluginActvity() {
        return pluginActvity.get();
    }
    public void setPluginActvity(String activity) {
        pluginActvity.set(activity);
    }

    public Credentials getPluginCredentials() {
        return pluginCredentials;
    }
    public void setPluginCredentials(Credentials credentials) {
        pluginCredentials = credentials;
    }
}

