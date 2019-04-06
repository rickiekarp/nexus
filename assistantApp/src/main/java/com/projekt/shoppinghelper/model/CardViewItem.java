package com.projekt.shoppinghelper.model;

/**
 * Created by sebastian on 04.12.17.
 */

public class CardViewItem {

    private String title, body;
    private int id;

    public CardViewItem(String title, String body, int id) {
        this.title = title;
        this.body = body;
        this.id = id;
    }

    public CardViewItem(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
