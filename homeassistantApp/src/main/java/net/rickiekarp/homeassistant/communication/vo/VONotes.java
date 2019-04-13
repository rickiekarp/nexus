package net.rickiekarp.homeassistant.communication.vo;

import java.util.Date;

/**
 * Created by sebastian on 17.11.17.
 */

public class VONotes {

    private String title;
    private int id;
    private Date dateAdded;

    public VONotes(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public VONotes(String title) {
        this.title = title;
    }

    public VONotes(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int noteID) {
        this.id = noteID;
    }

    public Date getDate() {
        return dateAdded;
    }

    public void setDate(Date date) {
        this.dateAdded = date;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
