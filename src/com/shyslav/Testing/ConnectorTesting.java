import com.shyslav.database.connector;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * Created by Shyshkin Vladyslav on 07.06.2016.
 */
public class ConnectorTesting {
    @Test
    public void databaseConnection() {
        try {
            org.junit.Assert.assertNotNull("connection null point",connector.connect());
        } catch (SQLException e) {
            System.out.println(e);
            org.junit.Assert.fail("connection error");
        }
    }
    @Test
    public void login(){
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            String username = "ivanov";
            String password = "ivanov";
            try (ResultSet resultSet = statement.executeQuery("select id, positionsID, cafeID, name, lastname, adress, birthdayDay, elogin, epassword from employees " +
                    " where elogin='" + username + "' and epassword='" + password + "'")) {
                org.junit.Assert.assertNotNull("Not found user",resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e);
            org.junit.Assert.fail("mysql connection error");
        }
    }
}
