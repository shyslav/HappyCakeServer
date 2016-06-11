package com.shyslav.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Shyshkin Vladyslav on 28.03.2016.
 */
public class employees implements Serializable {
    private int id;
    private int positionID;
    private int cafeID;
    private String name;
    private String lastname;
    private String address;
    private Date birthdayDay;
    private String elogin;
    private String epassword;

    public employees(int id, int positionID, int cafeID, String name, String lastname, String address, Date birthdayDay, String elogin, String epassword) {
        this.id = id;
        this.positionID = positionID;
        this.cafeID = cafeID;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.birthdayDay = birthdayDay;
        this.elogin = elogin;
        this.epassword = epassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public int getCafeID() {
        return cafeID;
    }

    public void setCafeID(int cafeID) {
        this.cafeID = cafeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthdayDay() {
        return birthdayDay;
    }

    public void setBirthdayDay(Date birthdayDay) {
        this.birthdayDay = birthdayDay;
    }

    public String getElogin() {
        return elogin;
    }

    public void setElogin(String elogin) {
        this.elogin = elogin;
    }

    public String getEpassword() {
        return epassword;
    }

    public void setEpassword(String epassword) {
        this.epassword = epassword;
    }
}
