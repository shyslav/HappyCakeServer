package com.shyslav.models;

/**
 * Created by Shyshkin Vladyslav on 07.05.2016.
 */
public class reservationData {
    private String name;
    private String phone;
    private String amountPeople;
    private String date;
    private String time;
    private String message;

    public reservationData(String name, String phone, String amountPeople, String date, String time, String message) {
        this.name = name;
        this.phone = phone;
        this.amountPeople = amountPeople;
        this.date = date;
        this.time = time;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmountPeople() {
        return amountPeople;
    }

    public void setAmountPeople(String amountPeople) {
        this.amountPeople = amountPeople;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
