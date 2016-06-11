package com.shyslav.models;

import java.io.Serializable;

public class orderListCook implements Serializable
{
    private int id;
    private int dishID;
    private String dishName;
    private int dishAmount;
    private String dishImage;
    private String dishReadyORnot;
    private int amount;
    private double price;

    public orderListCook(int id, int dishID, String dishName, int dishAmount, String dishImage, String dishReadyORnot, int amount, double price) {
        this.id = id;
        this.dishID = dishID;
        this.dishName = dishName;
        this.dishAmount = dishAmount;
        this.dishImage = dishImage;
        this.dishReadyORnot = dishReadyORnot;
        this.amount = amount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(int dishAmount) {
        this.dishAmount = dishAmount;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getDishReadyORnot() {
        return dishReadyORnot;
    }

    public void setDishReadyORnot(String dishReadyORnot) {
        this.dishReadyORnot = dishReadyORnot;
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
