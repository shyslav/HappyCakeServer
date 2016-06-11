package com.shyslav.Testing;

import com.shyslav.database.connector;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Shyshkin Vladyslav on 07.06.2016.
 */
public class ConnectorTesting {
    connector con;

    @Before
    public void initial() {
        con = new connector();
    }

    @Test
    public void notInitialize() {
        Assert.assertNotNull("Обьект не проинициализирован", con);
    }

    @Test
    public void lostData() {
        try {
            Assert.assertNotNull("Данные не введены. Конекшн не создан", con.connect().getMetaData());
        } catch (SQLException ex) {
            Assert.fail("Данные не введены. Конекшн не создан");
        }
    }

    @Test
    public void lostConnection() {
        Connection cont = null;
        try {
            cont = con.connect();
        } catch (SQLException e) {
            Assert.fail("Ошибка подключения. Подключение не возможно. Не верное подключение");
        }
    }

    @Test
    public void warningsConnection() {
        try (Connection cont = con.connect()) {
            Assert.assertNull(cont.getWarnings());
        } catch (SQLException e) {
            Assert.fail("Ошибка подключения");
        }
    }


}
