package net.rickiekarp.qaacc.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Projects {
    private final SimpleIntegerProperty projectID;
    private final SimpleStringProperty projectName;
    private final SimpleStringProperty projectXML;
    private final SimpleStringProperty projectAttribute;
    private final SimpleIntegerProperty projectAccBookmarkIdx;
    private final SimpleStringProperty projectAccBookmarkName;

    public Projects(int aProjectID, String aProject, String aXML, String aAttr, int accBMIdx, String accBMName) {

        this.projectID = new SimpleIntegerProperty(aProjectID);
        this.projectName = new SimpleStringProperty(aProject);
        this.projectXML = new SimpleStringProperty(aXML);
        this.projectAttribute = new SimpleStringProperty(aAttr);
        this.projectAccBookmarkIdx = new SimpleIntegerProperty(accBMIdx);
        this.projectAccBookmarkName = new SimpleStringProperty(accBMName);
    }

    public int getProjectID() {
        return projectID.get();
    }
    public String getProjectName() {
        return projectName.get();
    }
    public String getProjectXML() {
        return projectXML.get();
    }
    public String getProjectAttribute() {
        return projectAttribute.get();
    }
    public int getProjectAccBookmarkIdx() {
        return projectAccBookmarkIdx.get();
    }
    public void setProjectAccBookmarkIdx(int aBMID) {
        projectAccBookmarkIdx.set(aBMID);
    }
    public String getProjectAccBookmarkName() {
        return projectAccBookmarkName.get();
    }
    public void setProjectAccBookmarkName(String aBMName) { projectAccBookmarkName.set(aBMName);}
}
