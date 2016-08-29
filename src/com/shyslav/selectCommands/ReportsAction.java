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
public class ReportsAction {
    /**
     * Функция получения всех отзывов о ресторане
     *
     * @param id отзыва для выборки
     * @return лист отзывов о ресторане
     */
    public static ArrayList<_Reports> selectReports(int id) {
        ArrayList<_Reports> reports = new ArrayList<>();
        String query = " ";
        switch (id) {
            case 0:
                query = "select * from reports";
                break;

            default:
                query = "select * from reports where id  = " + id;
                break;
        }
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    reports.add(new _Reports(resultSet.getInt("id"),
                            resultSet.getString("author"),
                            resultSet.getString("rText"),
                            resultSet.getDate("rDate"),
                            resultSet.getString("mail"),
                            resultSet.getString("phone"),
                            resultSet.getString("vision")));
                }
                if (reports.size() != 0) {
                    return reports;
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