import com.happycake.sitemodels.Employees;
import com.happycake.sitemodels.HappyCakeNotifications;
import com.happycake.sitemodels.HappyCakeRoles;
import com.shyslav.controller.ServerClient;
import com.shyslav.controller.ServerStarApp;
import com.shyslav.controller.actions.ClientActions;
import com.shyslav.controller.pinger.ClientUpdatesPinger;
import com.shyslav.mysql.exceptions.DBException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Shyshkin Vladyslav on 07.06.2016.
 */
public class ClientPingerTest {
    private static ServerStarApp startApp;
    private static ClientActions client;
    private static Employees employees;

    @BeforeClass
    public static void beforeClass() throws Exception {
        startApp = new ServerStarApp();
        Thread thread = new Thread(() -> startApp.start("/etc/start_test.xml"));
        thread.setName("server");
        thread.start();
        while (!ServerStarApp.started) {
            Thread.sleep(500);
        }
    }

    @Before
    public void before() {
        try {
            ServerStarApp.storages.clear();
            //insert user
            Employees user = createUser();
            user.setPosition(HappyCakeRoles.ADMIN);
            ServerStarApp.storages.employeesStorage.save(user);
            //authorise
            client = successLogin();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void pingerTest() throws Exception {
        //insert user
        Employees user = createUser();
        user.setPosition(HappyCakeRoles.ADMIN);
        ServerStarApp.storages.employeesStorage.save(user);
        //authorise
        ClientActions client2 = successLogin();

        ClientUpdatesPinger pinger1 = new ClientUpdatesPinger(client, 100);
        ClientUpdatesPinger pinger2 = new ClientUpdatesPinger(client2, 100);
        assertTrue(pinger1.isWork());
        assertTrue(pinger2.isWork());

        //initialize listener
        StringBuilder builderClient1 = new StringBuilder();
        pinger1.addListener(HappyCakeNotifications.MESSAGE_TO_USERS, (event) -> builderClient1.append(event.getContext()));

        StringBuilder builderClient2 = new StringBuilder();
        pinger2.addListener(HappyCakeNotifications.MESSAGE_TO_USERS, (event) -> builderClient2.append(event.getContext()));
        Thread.sleep(1000);

        //call event to get listener
        client.sendMessage("ALL", "test");
        Thread.sleep(1000);
        assertTrue(builderClient2.toString().equals("test"));
        assertTrue(builderClient1.toString().isEmpty());

        //check statistic
        assertTrue(pinger1.getAmountEmptyAnswers() > 5);
        assertTrue(pinger1.getAmountNotEmptyAnswers() == 0);
        assertTrue(pinger2.getAmountNotEmptyAnswers() != 0);

        //off pinger thread
        pinger1.offPingerThread();
        Thread.sleep(1000);
        assertTrue(!pinger1.isWork());
    }

    /**
     * Success login to server
     *
     * @return client actions
     * @throws Exception
     */
    private static ClientActions successLogin() throws Exception {
        ServerClient client = new ServerClient();
        ClientActions clientActions = new ClientActions(client);
        employees = clientActions.login("admin", "admin").getObject(Employees.class);
        return clientActions;
    }

    /**
     * Create test user
     *
     * @return user id
     * @throws DBException
     */
    private static Employees createUser() {
        //create employee
        Employees employees = new Employees();
        employees.setPosition(HappyCakeRoles.ADMIN);
        employees.setAddress("123");
        employees.setBirthday(12345);
        employees.setCafeID(1);
        employees.setName("12345");
        employees.setLastname("123");
        employees.setLogin("admin");
        employees.setPassword("admin");
        return employees;
    }
}
