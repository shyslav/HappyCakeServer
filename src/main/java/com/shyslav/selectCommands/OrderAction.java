//package main.java.com.shyslav.selectCommands;
//
//import com.shyslav.database.DBConnector;
//import appmodels.*;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
///**
// * Created by Shyshkin Vladyslav on 21.05.2016.
// */
//public class OrderAction {
//    /**
//     * Функция плучения всех заказов из базы
//     *
//     * @param id - ид заказа
//     * @return лист заказов
//     */
//    public static ArrayList<_Order> selectOrders(int id) {
//        ArrayList<_Order> order = new ArrayList<>();
//        String query = " ";
//        switch (id) {
//            case 0:
//                query = "select *  from orders";
//                break;
//
//            default:
//                query = "select * from orders  where id = " + id;
//                break;
//        }
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            try (ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    order.add(new _Order(
//                            resultSet.getInt("id"),
//                            resultSet.getInt("employeeId"),
//                            resultSet.getDouble("fullPrice"),
//                            resultSet.getString("odate"),
//                            resultSet.getString("compliteORnot")));
//                }
//                if (order.size() != 0) {
//                    return order;
//                } else {
//                    return null;
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
//
//    /**
//     * Получение всех элементов заказа
//     *
//     * @param id - ид заказа
//     * @return лист всех элементов заказа
//     */
//    public static ArrayList<_OrderList> selectorderList(int id) {
//        ArrayList<_OrderList> order = new ArrayList<>();
//        String query = " ";
//        switch (id) {
//            case 0:
//                query = "select list.id as id, list.orderID as orderID ,list.dishID as dishID ,dish.name as dishName,list.amount as amount, list.price as price from orderlist list inner join dish dish on dish.id = list.dishID";
//                break;
//
//            default:
//                query = "select list.id as id, list.orderID as orderID ,list.dishID as dishID ,dish.name as dishName,list.amount as amount, list.price as price from orderlist list inner join dish dish on dish.id = list.dishID where orderID = " + id;
//                break;
//        }
//        try (Connection conn = DBConnector.connect()) {
//            Statement statement = conn.createStatement();
//            try (ResultSet resultSet = statement.executeQuery(query)) {
//                while (resultSet.next()) {
//                    order.add(new _OrderList(
//                            resultSet.getInt("id"),
//                            resultSet.getInt("orderID"),
//                            resultSet.getInt("dishID"),
//                            resultSet.getString("dishName"),
//                            resultSet.getInt("amount"),
//                            resultSet.getDouble("price")));
//                }
//                if (order.size() != 0) {
//                    return order;
//                } else {
//                    return null;
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
//}