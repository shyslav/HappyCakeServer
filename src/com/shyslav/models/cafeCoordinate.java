package com.shyslav.models;

import java.io.Serializable;

/**
 * Created by shyshkin_vlad on 22.04.16.
 */
public class cafeCoordinate implements Serializable {
    private int id;
    private String address;
    private String mobilePhone;
    private String cafeemail;

    public cafeCoordinate(int id, String address, String mobilePhone, String cafeemail) {
        this.id = id;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.cafeemail = cafeemail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCafeemail() {
        return cafeemail;
    }

    public void setCafeemail(String cafeemail) {
        this.cafeemail = cafeemail;
    }
}
