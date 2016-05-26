package com.shyslav.selectCommands;

import com.shyslav.database.connector;
import com.shyslav.models.CookOrder;
import com.shyslav.models.orderListCook;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Shyshkin Vladyslav on 25.05.2016.
 */
public class CookAction {
    public LinkedList<CookOrder> queue = new LinkedList<>();
    public LinkedList<CookOrder> start()
    {
        selectFromOrderList();
        return queue;
    }
    public LinkedList<CookOrder> get()
    {
        if(selectFromOrderList())
        {
            return queue;
        }
        return null;
    }
    public void close(int orders)
    {
        UpdateAction.update(new String [] {"update:","orders",String.valueOf(orders),"compliteORnot = '+'"}).equals("done");
    }

//    private boolean selectFromOrderList(String orderID)
//    {
//        queue.clear();
//        String query = "";
//        switch (orderID)
//        {
//            case "0":
//                query = "select ords.odate as date ,od.id as id,od.orderID as orderid,od.dishID as dishid,ds.name as dishname,od.amount as amount,od.price as price from orderlist od\n" +
//                        " inner join dish ds on ds.id = od.dishID \n" +
//                        " inner join orders ords on ords.id=od.orderID\n" +
//                        " where od.orderID in (select id from orders where compliteORnot = '-' order by odate asc)" +
//                        " order by date asc";
//            break;
//            default:
//                query = "select ords.odate as date ,od.id as id,od.orderID as orderid,od.dishID as dishid,ds.name as dishname,od.amount as amount,od.price as price from orderlist od\n" +
//                        " inner join dish ds on ds.id = od.dishID \n" +
//                        " inner join orders ords on ords.id=od.orderID\n" +
//                        " where od.orderID in (select id from orders where compliteORnot = '-' order by odate asc)\n" +
//                        " and ords.id = " +orderID + " order by date asc";
//                break;
//        }
//           try (Connection conn = connector.connect()) {
//            Statement statement = conn.createStatement();
//            try (ResultSet resultSet = statement.executeQuery(query)) {
//                boolean tmp = false;
//                while (resultSet.next()) {
//                    queue.add(new CookOrder(
//                            resultSet.getTimestamp("date"),
//                            resultSet.getInt("id"),
//                            resultSet.getInt("orderid"),
//                            resultSet.getInt("dishid"),
//                            resultSet.getString("dishname"),
//                            resultSet.getInt("amount"),
//                            resultSet.getDouble("price")));
//                    tmp = true;
//                }
//                if(tmp==true)
//                {
//                    return true;
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return false;
//    }



    private boolean selectFromOrderList()
    {
        queue.clear();
        String query =  "select ords.id as id, ords.employeeID as employeeID, CONCAT(emp.name,\" \",emp.lastname) as employeeFIO,\n" +
                        " ords.fullPrice as fullPrice, ords.odate as odate, ords.compliteORnot as compliteORnot from  orders ords \n" +
                        " inner join employees emp on ords.employeeID = emp.id\n" +
                        " where ords.compliteORnot = '-'\n" +
                        " order by ords.odate asc";
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                boolean tmp = false;
                while (resultSet.next()) {
                    ArrayList orders = selectOrderListCook(resultSet.getInt("id"));
                    if(orders!=null) {
                        queue.add(new CookOrder(resultSet.getInt("id"),
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
    private ArrayList<orderListCook> selectOrderListCook(int id)
    {
        ArrayList<orderListCook> ord = new ArrayList<>();
        String query = "select odlist.id as id,odlist.dishID as dishID,ds.name as dishName,ds.amount as dishAmount,ds.image as dishImage,\n" +
                " ds.readyORnot as dishReadyORnot,odlist.amount as amount,odlist.price as price from orderlist odlist\n" +
                " inner join dish ds on ds.id = odlist.dishID\n" +
                " where odlist.orderID = "+id+
                " order by  odlist.id asc";
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    ord.add(new orderListCook(resultSet.getInt("id"),
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