import com.happycake.sitemodels.*;
import com.shyslav.controller.ServerClient;
import com.shyslav.controller.ServerStarApp;
import com.shyslav.controller.actions.ClientActions;
import com.shyslav.defaults.ErrorCodes;
import com.shyslav.defaults.HappyCakeResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Shyshkin Vladyslav on 07.06.2016.
 */
public class ClientActionsTest {
    private final String realUserName = "admin";
    private final String realUserPass = "admin";

    @BeforeClass
    public static void beforeClass() throws Exception {
        Thread thread = new Thread(ServerStarApp::start);
        thread.setName("server");
        thread.start();
        while (!ServerStarApp.started) {
            Thread.sleep(1000);
        }
    }

    /**
     * Login test
     *
     * @throws Exception
     */
    @Test
    public void login() throws Exception {
        //success login
        ServerClient client = new ServerClient();
        ClientActions clientActions = new ClientActions(client);
        HappyCakeResponse login = clientActions.login(realUserName, realUserPass);
        assertNotNull(login);
        assertTrue(login.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((Employees) login.getObject(Employees.class)).getLogin().equals(realUserName));

        //wrong login
        HappyCakeResponse login1 = clientActions.login("admin123", "admin123");
        assertNotNull(login1);
        assertTrue(login1.getCode() == ErrorCodes.ACCESS_DENIED);
    }

    /**
     * Select news test
     *
     * @throws Exception
     */
    @Test
    public void selectNews() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectNews();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((NewsList) response.getObject(NewsList.class)).size() > 1);
    }

    /**
     * Select categories test
     *
     * @throws Exception
     */
    @Test
    public void selectCategories() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectCategories();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((CategoriesList) response.getObject(CategoriesList.class)).size() > 1);
    }

    /**
     * Select dishes test
     *
     * @throws Exception
     */
    @Test
    public void selectDishes() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectDish();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((DishesList) response.getObject(DishesList.class)).size() > 1);
    }

    /**
     * Select reservation test
     *
     * @throws Exception
     */
    @Test
    public void selectReservation() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectReservation();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((ReservationList) response.getObject(ReservationList.class)).size() > 1);
    }

    /**
     * Select preorders test
     *
     * @throws Exception
     */
    @Test
    public void selectPreorders() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectPreOrder();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((PreOrderList) response.getObject(PreOrderList.class)).size() > 1);
    }


    /**
     * Select employees test
     *
     * @throws Exception
     */
    @Test
    public void selectEmployees() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectEmployees();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((EmployeesList) response.getObject(EmployeesList.class)).size() > 1);
    }

    /**
     * Select reports test
     *
     * @throws Exception
     */
    @Test
    public void selectReports() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectReports();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((ReportsList) response.getObject(ReportsList.class)).size() > 1);
    }

    /**
     * Select cafe coordinate test
     *
     * @throws Exception
     */
    @Test
    public void selectCafeCoordinate() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectCafeCoordinate();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((CafeCoordinateList) response.getObject(CafeCoordinateList.class)).size() >= 1);
    }

    /**
     * Select position test
     *
     * @throws Exception
     */
    @Test
    public void selectPositions() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectPositions();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        assertTrue(((PositionsList) response.getObject(PositionsList.class)).size() > 1);
    }


    /**
     * Select orders test
     *
     * @throws Exception
     */
    @Test
    public void selectOrdersTest() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.selectOrders();
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);
        OrderList list = response.getObject(OrderList.class);
        assertTrue(list.size() > 1);
        assertTrue(list.get(0).getOrderDetails().size() >= 1);
    }

    /**
     * Delete test
     *
     * @throws Exception
     */
    @Test
    public void deleteTest() throws Exception {
        ClientActions client = successLogin();
        HappyCakeResponse response = client.deleteByID("test", "123");
        assertTrue(response.getCode() == ErrorCodes.WROND_REQUST);
    }


    /**
     * Add news test
     *
     * @throws Exception
     */
    @Test
    public void addNews() throws Exception {
        ClientActions client = successLogin();
        News news = new News();
        HappyCakeResponse response = client.addNews(news);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        news.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addNews(news);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }

    /**
     * Add category test
     *
     * @throws Exception
     */
    @Test
    public void addCategory() throws Exception {
        ClientActions client = successLogin();
        Category category = new Category();
        HappyCakeResponse response = client.addCategories(category);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        category.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addCategories(category);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }

    /**
     * Add dish test
     *
     * @throws Exception
     */
    @Test
    public void addDish() throws Exception {
        ClientActions client = successLogin();
        Dish dish = new Dish();
        HappyCakeResponse response = client.addDish(dish);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        dish.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addDish(dish);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }

    /**
     * Add reservation test
     *
     * @throws Exception
     */
    @Test
    public void addReservation() throws Exception {
        ClientActions client = successLogin();
        Reservation reservation = new Reservation();
        HappyCakeResponse response = client.addReservation(reservation);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        reservation.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addReservation(reservation);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }

    /**
     * Add preorder test
     *
     * @throws Exception
     */
    @Test
    public void addPreoder() throws Exception {
        ClientActions client = successLogin();
        PreOrder preOrder = new PreOrder();
        HappyCakeResponse response = client.addPreorder(preOrder);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        preOrder.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addPreorder(preOrder);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }

    /**
     * Add employee test
     *
     * @throws Exception
     */
    @Test
    public void addEmployee() throws Exception {
        ClientActions client = successLogin();
        Employees employees = new Employees();
        HappyCakeResponse response = client.addEmployee(employees);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        employees.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addEmployee(employees);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }

    /**
     * Add reports test
     *
     * @throws Exception
     */
    @Test
    public void addReports() throws Exception {
        ClientActions client = successLogin();
        Reports reports = new Reports();
        HappyCakeResponse response = client.addReports(reports);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        reports.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addReports(reports);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }


    /**
     * Add cafe coordinate test
     *
     * @throws Exception
     */
    @Test
    public void addCafeCoordinate() throws Exception {
        ClientActions client = successLogin();
        CafeCoordinate cafeCoordinate = new CafeCoordinate();
        HappyCakeResponse response = client.addCafeCoordinate(cafeCoordinate);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        cafeCoordinate.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addCafeCoordinate(cafeCoordinate);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }


    /**
     * Add position test
     *
     * @throws Exception
     */
    @Test
    public void addPosition() throws Exception {
        ClientActions client = successLogin();
        Position position = new Position();
        HappyCakeResponse response = client.addPosition(position);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        position.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addPosition(position);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }

    /**
     * Add order test
     *
     * @throws Exception
     */
    @Test
    public void addOrder() throws Exception {
        ClientActions client = successLogin();
        Order order = new Order();
        HappyCakeResponse response = client.addOrder(order);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        order.setId(Integer.MAX_VALUE);
        HappyCakeResponse response1 = client.addOrder(order);
        assertTrue(response1.getCode() == ErrorCodes.INTERNAL_ERROR);
    }


    @Test
    public void deleteNewsTest() {

    }

    /**
     * Success login to server
     *
     * @return client actions
     * @throws Exception
     */
    private ClientActions successLogin() throws Exception {
        ServerClient client = new ServerClient();
        ClientActions clientActions = new ClientActions(client);
        clientActions.login(realUserName, realUserPass);
        return clientActions;
    }
}
