package com.shyslav.selectCommands;

import com.shyslav.database.DBConnector;
import appmodels.*;

import java.sql.*;
import java.util.*;

/**
 * Created by Shyshkin Vladyslav on 25.05.2016.
 */
public class CookAction {
    public LinkedList<_CookOrder> queue = new LinkedList<>();

    /**
     * Функция которая начинает работу повара
     * @return не закрытые заказы на текущий момент
     */
    public LinkedList<_CookOrder> start()
    {
        selectFromOrderList();
        return queue;
    }

    /**
     * Функция получения всех данных незакрытых заказов
     * @return лист незакрытых заказов
     */
    public LinkedList<_CookOrder> get()
    {
        if(selectFromOrderList())
        {
            return queue;
        }
        return null;
    }

    /**
     * Функция закрытия заказа в базе
     * @param orders № заказа котрый выполнил повар
     */
    public void close(int orders)
    {
        //команда, таблица, №заказа, поле котрое нужно изменить
        UpdateAction.update(new String [] {"update:","_Order",String.valueOf(orders),"compliteORnot = '+'"});
    }

    /**
     * Функция селекта всех данных необходимых для работы повара
     * @return лист всех заказов которе нужно выполнить повару
     */
    private boolean selectFromOrderList()
    {
        queue.clear();
        String query =  "select ords.id as id, ords.employeeID as employeeID, CONCAT(emp.name,\" \",emp.lastname) as employeeFIO,\n" +
                        " ords.fullPrice as fullPrice, ords.odate as odate, ords.compliteORnot as compliteORnot from  orders ords \n" +
                        " inner join employees emp on ords.employeeID = emp.id\n" +
                        " where ords.compliteORnot = '-'\n" +
                        " order by ords.odate asc";
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                boolean tmp = false;
                while (resultSet.next()) {
                    //Вытащить все заказы для текущего заказа
                    ArrayList orders = selectOrderListCook(resultSet.getInt("id"));
                    if(orders!=null) {
                        queue.add(new _CookOrder(resultSet.getInt("id"),
                                resultSet.getInt("employeeID"),
                                resultSet.getString("employeeFIO"),
                                resultSet.getDouble("fullPrice"),
                                resultSet.getTimestamp("odate"),
                                resultSet.getString("compliteORnot"),
                                orders));
                        tmp = true;
                    }
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

    /**
     * Селект всех элементов заказа
     * @param id номер заказа для которого производить выборку
     * @return лист элементов в заказе для повара
     */
    private ArrayList<_OrderListCook> selectOrderListCook(int id)
    {
        ArrayList<_OrderListCook> ord = new ArrayList<>();
        String query = "select odlist.id as id,odlist.dishID as dishID,ds.name as dishName,ds.amount as dishAmount,ds.image as dishImage,\n" +
                " ds.readyORnot as dishReadyORnot,odlist.amount as amount,odlist.price as price from orderlist odlist\n" +
                " inner join dish ds on ds.id = odlist.dishID\n" +
                " where odlist.orderID = "+id+
                " order by  odlist.id asc";
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    ord.add(new _OrderListCook(resultSet.getInt("id"),
                            resultSet.getInt("dishID"),
                            resultSet.getString("dishName"),
                            resultSet.getInt("dishAmount"),
                            resultSet.getString("dishImage"),
                            resultSet.getString("dishReadyORnot"),
                            resultSet.getInt("amount"),
                            resultSet.getDouble("price")));
                }
                if(ord.size()!=0)
                {
                    return ord;
                }
                else
                {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

}