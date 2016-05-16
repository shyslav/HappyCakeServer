package com.shyslav.models;

/**
 * Created by Shyshkin Vladyslav on 06.05.2016.
 */
public class preOrder {
    private int dishID;
    private String dishName;
    private int amount;
    private double price;

    public preOrder(int dishID, String dishName, int amount, double price) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.amount = amount;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
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
