package com.shyslav.selectCommands;

import com.shyslav.database.connector;
import com.shyslav.models._Cassir;
import com.shyslav.models.category;
import com.shyslav.models.dish;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
}
