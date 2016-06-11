package com.shyslav.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shyshkin_vlad on 22.04.16.
 */
public class orders implements Serializable{
    private int id;
    private int employeeId;
    private double fullPrice;
    private String orderDate;
    private String compliteOrNot;

    public orders(int id, int employeeId, double fullPrice, String orderDate, String compliteOrNot) {
        this.id = id;
        this.employeeId = employeeId;
        this.fullPrice = fullPrice;
        this.orderDate = orderDate;
        this.compliteOrNot = compliteOrNot;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getCompliteOrNot() {
        return compliteOrNot;
    }

    public void setCompliteOrNot(String compliteOrNot) {
        this.compliteOrNot = compliteOrNot;
    }
}
