package com.rkarp.appcore.model;

import javafx.beans.property.SimpleStringProperty;

public class SettingsList {
    private final SimpleStringProperty settingName;
    private final SimpleStringProperty setting;
    private final SimpleStringProperty desc;

    public SettingsList(String settingName, String setting, String desc) {
        this.settingName = new SimpleStringProperty(settingName);
        this.setting = new SimpleStringProperty(setting);
        this.desc = new SimpleStringProperty(desc);
    }

    public String getSettingName() {
        return settingName.get();
    }
    public void setSettingName(String fName) {
        settingName.set(fName);
    }
    public String getSetting() {
        return setting.get();
    }
    public void setSetting(String fName) {
        setting.set(fName);
    }
    public String getDesc() {
        return desc.get();
    }
    public void setDesc(String fName) {
        desc.set(fName);
    }
}
