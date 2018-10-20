package net.rickiekarp.core.model.dto;

public class ApplicationDTO {
    private String identifier;
    private int version;
    private boolean updateEnable;

    public String getIdentifier() {
        return identifier;
    }

    public int getVersion() {
        return version;
    }

    public boolean isUpdateEnable() {
        return updateEnable;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setUpdateEnable(boolean updateEnable) {
        this.updateEnable = updateEnable;
    }

    @Override
    public String toString() {
        return "Application{" +
                "identifier='" + identifier + '\'' +
                ", version=" + version +
                ", updateEnable=" + updateEnable +
                '}';
    }

}
