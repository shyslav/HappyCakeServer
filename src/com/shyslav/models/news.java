package com.shyslav.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Shyshkin Vladyslav on 28.03.2016.
 */
public class news implements Serializable {
    private int id;
    private int authorID;
    private String name;
    private String text;
    private Date date;
    private String tegs;
    private int view;
    private String imageLink;

    public news(int id, int authorID, String name, String nText, Date nDate, String tegs, int view, String imageLink) {
        this.id = id;
        this.authorID = authorID;
        this.name = name;
        this.text = nText;
        this.date = nDate;
        this.tegs = tegs;
        this.view = view;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTegs() {
        return tegs;
    }

    public void setTegs(String tegs) {
        this.tegs = tegs;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
