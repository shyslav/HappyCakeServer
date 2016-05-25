package com.shyslav.selectCommands;

import com.shyslav.controller.Main;
import com.shyslav.database.connector;
import com.shyslav.models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Queue;
import java.util.jar.Pack200;

/**
 * Created by Shyshkin Vladyslav on 24.05.2016.
 */
public class CasirAction {
    public static ArrayList<_Cassir> CasirAction() {
        ArrayList<_Cassir> cas = new ArrayList<>();
        String query = "select * from category";
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    cas.add(new _Cassir(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("image")
                            ,CategoryAction.selectDish(resultSet.getInt("id"))));
                }
                if(cas.size()!=0)
                {
                    System.out.println(cas.size());
                    return cas;
                }
                else
                {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    public static String CasirAdd(ArrayList<orderList> ord, String cassirID, String fullPrice) {
        String[] data = {"insert", "orders", cassirID, fullPrice, "now()",
                "(select case when (select count(readyORnot) from dish where id in (" + dishID(ord) + ") and readyORnot = '+')<=0 then '+' else '-' end)"};
        UpdateAction.insert(data);
        orders od = orderInBase(cassirID);
        if (od == null) {
            System.out.println("Ошибка записи, данные не были внесены в базу");
            return "Ошибка записи, данные не были внесены в базу";
        } else {
            for (orderList item : ord) {
                UpdateAction.insert(new String[]{"insert", "orderlist",
                        String.valueOf(od.getId()),
                        String.valueOf(item.getDishID()),
                        String.valueOf(item.getAmount()),
                        String.valueOf(item.getPrice())});
            }
            Main.sendToCook(od.getId());
            if (od.getCompliteOrNot().equals("+")) {
                return "Отдайте клиенту его заказ";
            } else
            {
                return "Некоторые из блюд нужно готовить";
            }
        }
    }

    private static orders orderInBase(String cassirID)
    {
        orders od = null;
        String query = "select * from orders where employeeID = "+cassirID+" order by id desc limit 1";
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                             od = new orders(
                            resultSet.getInt("id"),
                            resultSet.getInt("employeeID"),
                            resultSet.getDouble("fullPrice"),
                            resultSet.getString("odate")
                            ,resultSet.getString("compliteORnot"));
                }
                if(od != null)
                {
                    return od;
                }
                else
                {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    private static String dishID(ArrayList<orderList> ord)
    {
        String result = "";
        for (int i = 0 ; i < ord.size();i++)
        {
            if(i != ord.size()-1)
                result += ord.get(i).getDishID() +",";
            else
                result += ord.get(i).getDishID();
        }
        return result;
    }
}
