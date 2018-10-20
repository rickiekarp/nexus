package com.rkarp.appcore.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by rickie on 3/12/2016.
 */
public class ChangelogEntry {

    private final SimpleStringProperty version;
    private final SimpleStringProperty date;
    private final SimpleStringProperty desc;

    public ChangelogEntry(String version, String date, String desc) {
        this.version = new SimpleStringProperty(version);
        this.date = new SimpleStringProperty(date);
        this.desc = new SimpleStringProperty(desc);
    }

    public String getVersion() {
        return version.get();
    }
    public String getDate() {
        return date.get();
    }
    public String getDesc() {
        return desc.get();
    }

}
