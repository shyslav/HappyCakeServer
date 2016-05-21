package com.shyslav.selectCommands;

import com.shyslav.database.connector;
import com.shyslav.models.dish;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Shyshkin Vladyslav on 21.05.2016.
 */
public class DeleteAction {
    public static String delete(String tableName, String id)
    {
        String query  = "delete from  "+tableName +" where id =" +id;
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            //System.out.println(query);
            statement.execute(query);
            } catch (SQLException e1) {
            System.out.println("Ошибка запроса"+e1);
            return "not found";
        }
        return "done";
    }
}
