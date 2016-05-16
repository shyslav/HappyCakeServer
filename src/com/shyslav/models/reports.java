package com.shyslav.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Shyshkin Vladyslav on 05.05.2016.
 */
public class reports implements Serializable {
    private int id;
    private String author;
    private String text;
    private Date date;
    private String mail;
    private String phone;
    private String vision;

    public reports(int id, String author, String rText, Date rDate, String mail, String phone, String vision) {
        this.id = id;
        this.author = author;
        this.text = rText;
        this.date = rDate;
        this.mail = mail;
        this.phone = phone;
        this.vision = vision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }
}
