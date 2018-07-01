package com.rkarp.homeserver.model;

public class Plugin {
    private String identifier;
    private String version;
    private String file;
    private String type;
    private String updateEnable;

    public Plugin(String identifier, String version, String file, String type, String updEnable) {
        this.identifier = identifier;
        this.version = version;
        this.file = file;
        this.type = type;
        this.updateEnable = updEnable;
    }

    @Override
    public String toString() {
        return "Plugin [identifier=" + identifier + ", version=" + version + "]";
    }


    public String getIdentifier() {
        return identifier;
    }

    public String getVersion() {
        return version;
    }

    public String getFile() {
        return file;
    }

    public String getType() {
        return type;
    }

    public String getUpdateEnable() {
        return updateEnable;
    }
}