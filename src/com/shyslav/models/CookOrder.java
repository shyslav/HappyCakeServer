package com.shyslav.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Shyshkin Vladyslav on 25.05.2016.
 */
public class CookOrder implements Serializable {
    private int orderID;
    private int employeeID;
    private String employeeFIO;
    private double fullPrice;
    private Timestamp odate;
    private String compliteORnot;
    private ArrayList<orderListCook> orderListCooks;

    public CookOrder(int orderID, int employeeID, String employeeFIO, double fullPrice, Timestamp odate, String compliteORnot, ArrayList<orderListCook> orderListCooks) {
        this.orderID = orderID;
        this.employeeID = employeeID;
        this.employeeFIO = employeeFIO;
        this.fullPrice = fullPrice;
        this.odate = odate;
        this.compliteORnot = compliteORnot;
        this.orderListCooks = new ArrayList<>(orderListCooks);
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeFIO() {
        return employeeFIO;
    }

    public void setEmployeeFIO(String employeeFIO) {
        this.employeeFIO = employeeFIO;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public Timestamp getOdate() {
        return odate;
    }

    public void setOdate(Timestamp odate) {
        this.odate = odate;
    }

    public String getCompliteORnot() {
        return compliteORnot;
    }

    public void setCompliteORnot(String compliteORnot) {
        this.compliteORnot = compliteORnot;
    }

    public ArrayList<orderListCook> getOrderListCooks() {
        return orderListCooks;
    }

    public void setOrderListCooks(ArrayList<orderListCook> orderListCooks) {
        this.orderListCooks = orderListCooks;
    }
}
