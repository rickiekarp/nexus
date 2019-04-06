package net.rickiekarp.homeserver.dto;

import java.util.Date;

public class ShoppingNoteDto {
    private int id;
    private String title;
    private int user_id;
    private Date dateAdded;
    private Date lastUpdated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date dateBought) {
        this.lastUpdated = dateBought;
    }

    @Override
    public String toString() {
        return "ShoppingNoteDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", user_id=" + user_id +
                ", dateAdded=" + dateAdded +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
