package com.rkarp.flc.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * This is the model class for all formats of the FileList (.txt / .html etc.)
 */
public class FilelistFormats {
    private final SimpleStringProperty filetypeName;
    private final SimpleStringProperty filetypeEnding;

    public FilelistFormats(String aFile1, String aFile2 ) {

        this.filetypeName = new SimpleStringProperty(aFile1);
        this.filetypeEnding = new SimpleStringProperty(aFile2);
    }

    public String getFileTypeName() {
        return filetypeName.get();
    }
    public void setFiletypeName(String aFile1) {
        filetypeName.set(aFile1);
    }

    public String getFileTypeEnding() {
        return filetypeEnding.get();
    }
    public void setFiletypeEnding(String aFile2) {
        filetypeEnding.set(aFile2);
    }
}
