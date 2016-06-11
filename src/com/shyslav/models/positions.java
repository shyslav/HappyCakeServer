package com.shyslav.models;

import java.io.Serializable;

/**
 * Created by Shyshkin Vladyslav on 16.05.2016.
 */
public class positions implements Serializable{
    private int id;
    private String name;
    private double salary;

    public positions(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
