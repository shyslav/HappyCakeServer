package com.shyslav.models;

import java.io.Serializable;

/**
 * Created by Shyshkin Vladyslav on 16.05.2016.
 */
public class preOrderTable implements Serializable {
    private int reservID;
    private String dishName;
    private int amount;
    private double price;

    public preOrderTable(int reservID, String dishName, int amount, double price) {
        this.reservID = reservID;
        this.dishName = dishName;
        this.amount = amount;
        this.price = price;
    }

    public int getReservID() {
        return reservID;
    }

    public void setReservID(int reservID) {
        this.reservID = reservID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
