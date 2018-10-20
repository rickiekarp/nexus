package com.rkarp.flc.model;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.flc.controller.FilelistController;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;

/**
 * This is the model class for all files of the filelist.
 */
public class Filelist {
    private final SimpleStringProperty filename;
    private final SimpleStringProperty filetype;
    private final SimpleStringProperty filepath;
    private final SimpleLongProperty size;
    private final SimpleStringProperty creationDate;
    private final SimpleStringProperty moodifDate;
    private final SimpleStringProperty lastAccessDate;
    private final SimpleStringProperty isHidden;

    public Filelist(String aName, String aType, String aPath, long aSize, String aCreationTime, String aMoodifDate, String aAccessDate, boolean hidden ) {

        this.filename = new SimpleStringProperty(aName);
        this.filetype = new SimpleStringProperty(aType);
        this.filepath = new SimpleStringProperty(aPath);
        this.size = new SimpleLongProperty(aSize);
        this.creationDate = new SimpleStringProperty(aCreationTime);
        this.moodifDate = new SimpleStringProperty(aMoodifDate);
        this.lastAccessDate = new SimpleStringProperty(aAccessDate);

        if (hidden) { this.isHidden = new SimpleStringProperty(LanguageController.getString("yes")); }
        else { this.isHidden = new SimpleStringProperty(LanguageController.getString("no")); }
    }

    /**
     * Returns the filesize in a certain format
     */
    public static long calcFileSize(File file) {

        switch (FilelistController.UNIT_IDX) {
            case 0: return file.length();
            case 1:
                if (file.length() > 0 && file.length() < 1024) { return 1; }
                else { return file.length() / 1024; }
            case 2: return file.length() / 1024 / 1024;
            case 3: return file.length() / 1024 / 1024 / 1024;
            case 4: return file.length() / 1024 / 1024 / 1024 / 1024;
        }
        return -1;
    }

    /**
     * Returns the file extension of a given fileName
     */
    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1).toUpperCase();
        }
        return extension;
    }

    public String getFilename() {
        return filename.get();
    }
    public void setFilename(String aFile1) {
        filename.set(aFile1);
    }

    public String getFiletype() {
        return filetype.get();
    }
    public void setFiletype(String aFile2) {
        filetype.set(aFile2);
    }

    public String getFilepath() {
        return filepath.get();
    }
    public void setFilepath(String aFile3) {
        filepath.set(aFile3);
    }

    public long getSize() {
        return size.get();
    }
    public void setSize(long aSize) {
        size.set(aSize);
    }

    public String getCreationDate() {
        return creationDate.get();
    }
    public void setCreationDate(String aCreationDate) {
        creationDate.set(aCreationDate);
    }

    public String getLastModif() {
        return moodifDate.get();
    }
    public void setLastModif(String aFile5) {
        moodifDate.set(aFile5);
    }

    public String getLastAccessDate() {
        return lastAccessDate.get();
    }
    public void setLastAccessDate(String aAccessDate) {
        lastAccessDate.set(aAccessDate);
    }

    public String getIsHidden() {
        return isHidden.get();
    }
    public void setIsHidden(String hidden) {
        isHidden.set(hidden);
    }
}
