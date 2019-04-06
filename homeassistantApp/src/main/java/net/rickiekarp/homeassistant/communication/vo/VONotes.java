package net.rickiekarp.homeassistant.communication.vo;

import java.util.Date;

/**
 * Created by sebastian on 17.11.17.
 */

public class VONotes {

    private String content, title;
    private int noteId;
    private Date dateAdded;

    public VONotes(String content, String title, int noteId) {
        this.content = content;
        this.title = title;
        this.noteId = noteId;
    }

    public VONotes(String title, String content) {
        this.content = content;
        this.title = title;
    }

    public VONotes(int noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteID) {
        this.noteId = noteId;
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
