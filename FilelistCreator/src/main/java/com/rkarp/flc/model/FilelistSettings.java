package com.rkarp.flc.model;

public class FilelistSettings {

    private String title, desc;
    private boolean hidden;

    public FilelistSettings(String title, String desc, boolean onHidden) {
        this.title = title;
        this.desc = desc;
        this.hidden = onHidden;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

}