package com.shyslav.selectCommands;

import com.shyslav.database.connector;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Shyshkin Vladyslav on 25.05.2016.
 */
public class CookAction {
    public LinkedList<CookOrderList> queue = new LinkedList<>();
    public void start()
    {
        selectFromOrderList("0");
    }
    public LinkedList<CookOrderList> add(int orders)
    {
        if(selectFromOrderList(String.valueOf(orders)))
        {
            return queue;
        }
        return null;
    }
    public void close(int orders)
    {
        UpdateAction.update(new String [] {"update:","orders",String.valueOf(orders),"compliteORnot = '+'"}).equals("done");
        start();
    }

    private boolean selectFromOrderList(String orderID)
    {
        queue.clear();
        String query = "";
        switch (orderID)
        {
            case "0":
                query = "select ords.odate as date ,od.id as id,od.orderID as orderid,od.dishID as dishid,ds.name as dishname,od.amount as amount,od.price as price from orderlist od\n" +
                        " inner join dish ds on ds.id = od.dishID \n" +
                        " inner join orders ords on ords.id=od.orderID\n" +
                        " where od.orderID in (select id from orders where compliteORnot = '-' order by odate asc)" +
                        " order by date asc";
            break;
            default:
                query = "select ords.odate as date ,od.id as id,od.orderID as orderid,od.dishID as dishid,ds.name as dishname,od.amount as amount,od.price as price from orderlist od\n" +
                        " inner join dish ds on ds.id = od.dishID \n" +
                        " inner join orders ords on ords.id=od.orderID\n" +
                        " where od.orderID in (select id from orders where compliteORnot = '-' order by odate asc)\n" +
                        " and ords.id = " +orderID + " order by date asc";
                break;
        }
           try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                boolean tmp = false;
                while (resultSet.next()) {
                    queue.add(new CookOrderList(
                            resultSet.getTimestamp("date"),
                            resultSet.getInt("id"),
                            resultSet.getInt("orderid"),
                            resultSet.getInt("dishid"),
                            resultSet.getString("dishname"),
                            resultSet.getInt("amount"),
                            resultSet.getDouble("price")));
                    tmp = true;
                }
                if(tmp==true)
                {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}

class CookOrderList
{
    private Timestamp date;
    private int id;
    private int orderID;
    private int dishID;
    private String dishName;
    private int amount;
    private double price;

    public CookOrderList(Timestamp date, int id, int orderID, int dishID, String dishName, int amount, double price) {
        this.date = date;
        this.id = id;
        this.orderID = orderID;
        this.dishID = dishID;
        this.dishName = dishName;
        this.amount = amount;
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getDishID() {
        return dishID;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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