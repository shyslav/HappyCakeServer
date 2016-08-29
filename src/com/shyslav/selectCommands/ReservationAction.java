package com.shyslav.selectCommands;

import com.shyslav.database.DBConnector;
import appmodels.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Shyshkin Vladyslav on 16.05.2016.
 */
public class ReservationAction {
    /**
     * Функция получения всех данных по резервации
     *
     * @param id - номер конкретного резерва
     * @return - лист резервов
     */
    public static ArrayList<_Reservation> selectReservation(int id) {
        ArrayList<_Reservation> reservation = new ArrayList<>();
        String query = " ";
        switch (id) {
            case 0:
                query = "select * from reservation";
                break;

            default:
                query = "select * from reservation where id  = " + id;
                break;
        }
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    reservation.add(new _Reservation(
                            resultSet.getInt("id"),
                            resultSet.getInt("cafeID"),
                            resultSet.getString("clientName"),
                            resultSet.getString("clientPhone"),
                            resultSet.getDate("rDate"),
                            resultSet.getTime("rTime"),
                            resultSet.getString("confirmORnot"),
                            resultSet.getInt("amountPeople"),
                            resultSet.getString("description")));
                }
                if (reservation.size() != 0) {
                    return reservation;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Функция получения предзаказов
     *
     * @param id - номер резерва для получения предзаказа
     * @return - лист предзаказов
     */
    public static ArrayList<_PreOrderTable> selectPreOrder(int id) {
        ArrayList<_PreOrderTable> preOrderTable = new ArrayList<>();
        String query = " ";
        switch (id) {
            case 0:
                query = "select p.id as resid, p.reservID as reservID,d.name as dishName, p.amount as amount, p.price as price  from preorder p\n" +
                        "inner join dish d on d.id = p.dishID\n" +
                        "inner join reservation r on r.id=p.reservID";
                break;

            default:
                query = "select p.id as resid,p.reservID as reservID,d.name as dishName, p.amount as amount, p.price as price  from preorder p\n" +
                        "inner join dish d on d.id = p.dishID\n" +
                        "inner join reservation r on r.id=p.reservID where r.id  = " + id;
                break;
        }
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    preOrderTable.add(new _PreOrderTable(
                            resultSet.getInt("resid"),
                            resultSet.getInt("reservID"),
                            resultSet.getString("dishName"),
                            resultSet.getInt("amount"),
                            resultSet.getDouble("price")));
                }
                if (preOrderTable.size() != 0) {
                    return preOrderTable;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

}

