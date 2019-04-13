package net.rickiekarp.homeassistant.model;

/**
 * Created by sebastian on 04.12.17.
 */

public class CardViewItem {

    private String title;
    private int id;

    public CardViewItem(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public CardViewItem(String title) {
        this.title = title;
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

    public void setId(int id) {
        this.id = id;
    }
}
