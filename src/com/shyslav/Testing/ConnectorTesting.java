import com.shyslav.database.DBConnector;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.*;

/**
 * Created by Shyshkin Vladyslav on 07.06.2016.
 */
public class ConnectorTesting {
    @Test
    public void databaseConnection() {
        try {
           Assert.assertNotNull("connection null point", DBConnector.connect());
        } catch (SQLException e) {
            System.out.println(e);
            Assert.fail("connection error");
        }
    }
    @Test
    public void login(){
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            String username = "ivanov";
            String password = "ivanov";
            try (ResultSet resultSet = statement.executeQuery("select id, positionsID, cafeID, name, lastname, adress, birthdayDay, elogin, epassword from employees " +
                    " where elogin='" + username + "' and epassword='" + password + "'")) {
               Assert.assertNotNull("Not found user",resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e);
            Assert.fail("mysql connection error");
        }
    }
}
