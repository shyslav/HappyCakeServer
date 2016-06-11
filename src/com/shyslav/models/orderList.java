package com.shyslav.models;

import java.io.Serializable;

/**
 * Created by Shyshkin Vladyslav on 21.05.2016.
 */
public class orderList implements Serializable {
    private int id;
    private int orderId;
    private int dishID;
    private String dishName;
    private int amount;
    private double price;

    public orderList(int id, int orderId, int dishID, String dishName, int amount, double price) {
        this.id = id;
        this.orderId = orderId;
        this.dishID = dishID;
        this.dishName = dishName;
        this.amount = amount;
        this.price = price;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
