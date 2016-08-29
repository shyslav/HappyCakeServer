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
public class EmployeeAction {
    /**
     * Функция получения всех данных по сотрудниках
     *
     * @param id - ид сотрудника по которому можно сделать выборку
     * @return - лист сотрудников
     */
    public static ArrayList<_Employee> selectEmployees(int id) {
        ArrayList<_Employee> employees = new ArrayList<>();
        String query = " ";
        switch (id) {
            case 0:
                query = "select * from employees";
                break;

            default:
                query = "select * from employees where id  = " + id;
                break;
        }
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    employees.add(new _Employee(
                            resultSet.getInt("id"),
                            resultSet.getInt("positionsID"),
                            resultSet.getInt("cafeID"),
                            resultSet.getString("name"),
                            resultSet.getString("lastname"),
                            resultSet.getString("adress"),
                            resultSet.getDate("birthdayDay"),
                            resultSet.getString("elogin"),
                            resultSet.getString("epassword")));
                }
                if (employees.size() != 0) {
                    return employees;
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
     * Функция получения всех данных по расположению кафе
     *
     * @param id - ид кафе в базе
     * @return лист размещения кафе в базе
     */
    public static ArrayList<_CafeCoordinate> selectcafeCoordinate(int id) {
        ArrayList<_CafeCoordinate> cafeCoordinate = new ArrayList<>();
        String query = " ";
        switch (id) {
            case 0:
                query = "select * from cafecoordinate";
                break;

            default:
                query = "select * from cafecoordinate where id  = " + id;
                break;
        }
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    cafeCoordinate.add(new _CafeCoordinate(
                            resultSet.getInt("id"),
                            resultSet.getString("adress"),
                            resultSet.getString("mobilePhone"),
                            resultSet.getString("cafeemail")));
                }
                if (cafeCoordinate.size() != 0) {
                    return cafeCoordinate;
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
     * Получение позиций(рабочих мест) ресторана
     *
     * @param id - ид позиции
     * @return - лист позиций
     */
    public static ArrayList<_Positions> selectPositions(int id) {
        ArrayList<_Positions> positions = new ArrayList<>();
        String query = " ";
        switch (id) {
            case 0:
                query = "select * from positions";
                break;

            default:
                query = "select * from positions where id  = " + id;
                break;
        }
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    positions.add(new _Positions(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("salary")));
                }
                if (positions.size() != 0) {
                    return positions;
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
