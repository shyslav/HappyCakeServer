import com.shyslav.database.connector;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Shyshkin Vladyslav on 07.06.2016.
 */
public class CasirActionTest {
    @Test
    public void databaseConnection() {
        try {
            Assert.assertNotNull("connection null point",connector.connect());
        } catch (SQLException e) {
            System.out.println(e);
            Assert.fail("connection error");
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
               Assert.assertNotNull("Not found user",resultSet);
           }
       } catch (SQLException e) {
           System.out.println(e);
           Assert.fail("mysql connection error");
       }
   }
}
