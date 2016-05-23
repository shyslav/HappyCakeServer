package com.shyslav.selectCommands;


import com.shyslav.database.connector;
import com.shyslav.models.dish;

import java.sql.*;
import java.util.Arrays;
import java.util.jar.Pack200;

/**
 * Created by Shyshkin Vladyslav on 22.05.2016.
 */
public class UpdateAction {
    public static String selectToUpdate(String tableName, String what, String id)
    {
        String result = "";
        String query = "select "+what+" from "+tableName+" where id = " + id;
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result = resultSet.getString(1);
                }
                if(result.isEmpty()&& result==null && result.length()==0)
                {
                    return null;
                }
                else
                {
                    return result;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    public static String update(String [] date)
    {
        String [] tmp = Arrays.copyOfRange(date,3,date.length);
        for (int i = 0; i<tmp.length;i++)
        {
            if(tmp[i].contains("|"))
            {
                tmp[i] = tmp[i].replace("|",":");
            }
        }
        String query = "update "+date[1]+" SET "+String.join(",",tmp) +" where id = " + date[2];
        System.out.println(query);
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            statement.execute(query);
        } catch (SQLException ex) {
            System.out.println("Ошибка запроса"+ex);
            return "not found";
        }
        return "done";
    }
    public static String insert(String [] date)
    {
        String insertValues [] = Arrays.copyOfRange(date,2,date.length);
        for (int i = 0; i<insertValues.length;i++)
        {
            if(insertValues[i].contains("|"))
            {
                insertValues[i]=insertValues[i].replace("|",":");
            }
        }
        String query = "insert into "+date[1]+" values( LAST_INSERT_ID(),"+String.join(",",insertValues)+")";
        System.out.println(query);
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            statement.execute(query);
        } catch (SQLException ex) {
            System.out.println("Ошибка запроса"+ex);
            return "not found";
        }
        return "done";
    }
}
