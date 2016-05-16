package com.shyslav.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by shyshkin_vlad on 22.04.16.
 */
public class reservation implements Serializable {
    private int id;
    private int cafeId;
    private String clientName;
    private String clientPhone;
    private Date date;
    private Time time;
    private String confirmORnot;
    private int amountPeople;
    private String description;

    public reservation(int id, int cafeId, String clientName, String clientPhone, Date rDate, Time rTime, String confirmORnot, int amountPeople, String description) {
        this.id = id;
        this.cafeId = cafeId;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.date = rDate;
        this.time = rTime;
        this.confirmORnot = confirmORnot;
        this.amountPeople = amountPeople;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCafeId() {
        return cafeId;
    }

    public void setCafeId(int cafeId) {
        this.cafeId = cafeId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getConfirmORnot() {
        return confirmORnot;
    }

    public void setConfirmORnot(String confirmORnot) {
        this.confirmORnot = confirmORnot;
    }

    public int getAmountPeople() {
        return amountPeople;
    }

    public void setAmountPeople(int amountPeople) {
        this.amountPeople = amountPeople;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
