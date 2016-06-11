package com.shyslav.database;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Shyshkin Vladyslav on 27.03.2016.
 */
public class connector {
    /**
     * Фукция подлкючения к базе
     * @return драйвер подключения
     * @throws SQLException
     */
    public static Connection connect() throws SQLException {
        Properties prop = new Properties();
        //Считать с файла значения для подключения к базе
        try(InputStream in = connector.class.getResourceAsStream("database.properties")){
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driver = prop.getProperty("jdbc.drivers");
        if(driver!=null){
            System.setProperty("jdbc.drivers",driver);
        }
        String url = prop.getProperty("jdbc.url");
        String username = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        return DriverManager.getConnection(url,username,password);
    }

}
