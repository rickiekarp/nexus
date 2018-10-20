package com.rkarp.flc.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This is the model class for all sub-directories of the filelist.
 * dir: Path of the sub-directory
 * filesindir: How many files are in the directory
 */
public class Directorylist {
    private final SimpleStringProperty dir;
    private final SimpleIntegerProperty filesTotal;
    private final SimpleIntegerProperty filesindir;
    private final SimpleIntegerProperty foldersindir;
    private final SimpleLongProperty filesizeindir;

    public Directorylist(String directory, int filestotal,int fileAmount, int folderAmount, long fileSize) {

        this.dir = new SimpleStringProperty(directory);
        this.filesTotal = new SimpleIntegerProperty(filestotal);
        this.filesindir = new SimpleIntegerProperty(fileAmount);
        this.foldersindir = new SimpleIntegerProperty(folderAmount);
        this.filesizeindir = new SimpleLongProperty(fileSize);
    }

    public String getDir() {
        return dir.get();
    }
    public void setDir(String aFile1) {
        dir.set(aFile1);
    }

    public int getFilesTotal() {
        return filesTotal.get();
    }
    public void setFilesTotal(int amount) {
        filesTotal.set(getFilesTotal() + amount);
    }
    public void setFilesFromTotal(int amount) {
        filesTotal.set(getFilesTotal() - amount);
    }

    public int getFilesInDir() {
        return filesindir.get();
    }
    public void setFilesInDir(int amount) {
        filesindir.set(getFilesInDir() + amount);
    }
    public void setFilesFromDir(int amount) {
        filesindir.set(getFilesInDir() - amount);
    }

    public int getFoldersInDir() {
        return foldersindir.get();
    }
    public void setFoldersInDir(int amount) {
        foldersindir.set(getFoldersInDir() + amount);
    }
    public void setFoldersFromDir(int amount) {
        foldersindir.set(getFoldersInDir() - amount);
    }

    public long getFileSizeInDir() {
        return filesizeindir.get();
    }
    public void setFileSizeInDir(long size) {
        filesizeindir.set(getFileSizeInDir() + size);
    }
    public void setFileSizeFromDir(long size) {
        filesizeindir.set(getFileSizeInDir() - size);
    }
}
