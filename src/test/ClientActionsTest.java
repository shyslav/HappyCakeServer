import com.happycake.sitemodels.*;
import com.shyslav.controller.ServerClient;
import com.shyslav.controller.ServerStarApp;
import com.shyslav.controller.actions.ClientActions;
import com.shyslav.defaults.ErrorCodes;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.utils.LazyDate;
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


    /**
     * Delete news test
     *
     * @throws Exception
     */
    @Test
    public void deleteNews() throws Exception {
        ClientActions client = successLogin();

        //add news
        News news = new News();
        news.setName("Test");
        news.setAuthorID(1);
        news.setImageLink("Test");
        news.setTegs("Test");
        news.setText("Test");
        news.setView(1);

        client.addNews(news);

        //load all news
        NewsList list = client.selectNews().getObject(NewsList.class);
        News lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getName().equals("Test"));

        //delete news
        client.deleteNews(lastElement.getId());

        //check if news was deleted
        list = client.selectNews().getObject(NewsList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(!lastElement.getName().equals("Test"));
    }

    /**
     * Delete categories test
     *
     * @throws Exception
     */
    @Test
    public void deleteCategories() throws Exception {
        ClientActions client = successLogin();

        //add categories
        Category category = new Category();
        category.setName("Test");
        category.setDescription("Test");
        category.setImage(new byte[]{1, 2, 3, 4, 5});
        client.addCategories(category);

        //load all categories
        CategoriesList list = client.selectCategories().getObject(CategoriesList.class);
        Category lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getName().equals("Test"));

        //delete category
        client.deleteCategories(lastElement.getId());

        //check if category was deleted
        list = client.selectCategories().getObject(CategoriesList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(!lastElement.getName().equals("Test"));
    }

    /**
     * Delete dish test
     *
     * @throws Exception
     */
    @Test
    public void deleteDish() throws Exception {
        ClientActions client = successLogin();

        //add dish
        Dish dish = new Dish();
        dish.setCategoryId(1);
        dish.setName("Test");
        dish.setAmount(1);
        dish.setPrice(Integer.MAX_VALUE);
        dish.setImage(new byte[]{1, 2, 3, 4, 5});
        client.addDish(dish);


        //load all dishes
        DishesList list = client.selectDish().getObject(DishesList.class);
        Dish lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getName().equals("Test"));

        //delete dish
        client.deleteDish(lastElement.getId());

        //check if dish was deleted
        list = client.selectDish().getObject(DishesList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(!lastElement.getName().equals("Test"));
    }

    /**
     * Delete test on reservation
     *
     * @throws Exception
     */
    @Test
    public void deleteReservation() throws Exception {
        ClientActions client = successLogin();

        //add reservation
        Reservation reservation = new Reservation();
        reservation.setCafeId(1);
        reservation.setClientName("Test");
        reservation.setAmountPeople(1);
        reservation.setClientPhone("test");
        reservation.setDescription("Test");
        client.addReservation(reservation);

        //load all reservations
        ReservationList list = client.selectReservation().getObject(ReservationList.class);
        Reservation lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getClientName().equals("Test"));

        //delete reservations
        client.deleteReservation(lastElement.getId());

        //check if category was deleted
        list = client.selectReservation().getObject(ReservationList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(!lastElement.getClientName().equals("Test"));
    }


    /**
     * Delete test on preOrder
     *
     * @throws Exception
     */
    @Test
    public void deletePreOrder() throws Exception {
        ClientActions client = successLogin();

        //add preOrder
        PreOrder preOrder = new PreOrder();
        preOrder.setDishID(1);
        preOrder.setAmount(1);
        preOrder.setReservationID(1);
        preOrder.setPrice(Integer.MAX_VALUE);


        client.addPreorder(preOrder);

        //load all preOrders
        PreOrderList list = client.selectPreOrder().getObject(PreOrderList.class);
        PreOrder lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getPrice() == Integer.MAX_VALUE);

        //delete preOrders
        client.deletePreOrder(lastElement.getId());

        //check if preOrder was deleted
        list = client.selectPreOrder().getObject(PreOrderList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(lastElement.getPrice() != Integer.MAX_VALUE);
    }


    /**
     * Delete test on employee
     *
     * @throws Exception
     */
    @Test
    public void deleteEmployee() throws Exception {
        ClientActions client = successLogin();

        //add employee
        Employees employee = new Employees();
        employee.setCafeID(1);
        employee.setPositionID(1);
        employee.setBirthday(14051996);
        employee.setName("Test");
        employee.setLastname("Test");
        employee.setAddress("Test");
        employee.setLogin("Test");
        employee.setPassword("Test");
        client.addEmployee(employee);

        //load all employees
        EmployeesList list = client.selectEmployees().getObject(EmployeesList.class);
        Employees lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getName().equals("Test"));

        //delete employees
        client.deleteEmployees(lastElement.getId());

        //check if employee was deleted
        list = client.selectEmployees().getObject(EmployeesList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(!lastElement.getName().equals("Test"));
    }

    /**
     * Delete test on reports
     *
     * @throws Exception
     */
    @Test
    public void deleteReports() throws Exception {
        ClientActions client = successLogin();

        //add report

        Reports reports = new Reports();
        reports.setAuthor("Test");
        reports.setMail("Test");
        reports.setPhone("Test");
        reports.setText("Test");

        client.addReports(reports);

        //load all reports
        ReportsList list = client.selectReports().getObject(ReportsList.class);
        Reports lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getAuthor().equals("Test"));

        //delete report
        client.deleteReports(lastElement.getId());

        //check if report was deleted
        list = client.selectReports().getObject(ReportsList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(!lastElement.getAuthor().equals("Test"));
    }

    /**
     * Delete test on CafeCoordinate
     *
     * @throws Exception
     */
    @Test
    public void deleteCafeCoordinate() throws Exception {
        ClientActions client = successLogin();

        //add cafeCoordinate

        CafeCoordinate cafeCoordinate = new CafeCoordinate();
        cafeCoordinate.setAddress("Test");
        cafeCoordinate.setEmail("Test");
        cafeCoordinate.setMobilePhone("+380913030598");
        client.addCafeCoordinate(cafeCoordinate);

        //load all cafeCoordinate
        CafeCoordinateList list = client.selectCafeCoordinate().getObject(CafeCoordinateList.class);
        CafeCoordinate lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getAddress().equals("Test"));

        //delete cafeCoordinate
        client.deleteCafeCoordinate(lastElement.getId());

        //check if cafeCoordinate was deleted
        list = client.selectCafeCoordinate().getObject(CafeCoordinateList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(!lastElement.getAddress().equals("Test"));
    }

    /**
     * Delete test on positions
     *
     * @throws Exception
     */
    @Test
    public void deletePositions() throws Exception {
        ClientActions client = successLogin();

        //add Position
        Position position = new Position();
        position.setName("Test");
        position.setSalary(Integer.MAX_VALUE);

        client.addPosition(position);

        //load all positions
        PositionsList list = client.selectPositions().getObject(PositionsList.class);
        Position lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getName().equals("Test"));

        //delete position
        client.deletePositions(lastElement.getId());

        //check if position was deleted
        list = client.selectPositions().getObject(PositionsList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(!lastElement.getName().equals("Test"));
    }

    /**
     * Delete test on orders
     *
     * @throws Exception
     */
    @Test
    public void deleteOrders() throws Exception {
        ClientActions client = successLogin();

        //add order
        Order order = generateTestOrderWithDetails();
        client.saveOrderWithDetails(order);

        //load all orders
        OrderList list = client.selectOrders().getObject(OrderList.class);
        Order lastElement = list.get(list.size() - 1);
        assertTrue(list.size() >= 1);
        assertTrue(lastElement.getFullPrice() == Integer.MAX_VALUE);

        //delete order
        client.deleteOrders(lastElement.getId());

        //check if order was deleted
        list = client.selectOrders().getObject(OrderList.class);
        lastElement = list.get(list.size() - 1);
        assertTrue(lastElement.getFullPrice() != Integer.MAX_VALUE);
        if (lastElement.getOrderDetails().size() != 0) {
            assertTrue(lastElement.getOrderDetails().get(0).getPrice() != Integer.MAX_VALUE);
        }
    }

    /**
     * Save order with details
     *
     * @throws Exception
     */
    @Test
    public void saveOrdersWithDetails() throws Exception {
        ClientActions client = successLogin();

        Order order = generateTestOrderWithDetails();
        HappyCakeResponse response = client.saveOrderWithDetails(order);

        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        OrderList list = client.selectOrders().getObject(OrderList.class);
        assertNotNull(list);

        Order last = list.get(list.size() - 1);
        assertTrue(last.getOrderDetails().size() == 1);
        assertTrue(last.getOrderDetails().get(0).getPrice() == Integer.MAX_VALUE);

        HappyCakeResponse deleteOrders = client.deleteOrders(last.getId());
        assertTrue(deleteOrders.getCode() == ErrorCodes.SUCCESS);
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

    /**
     * Generate test order with order details
     *
     * @return order with details
     */
    private Order generateTestOrderWithDetails() {
        //set order required fields
        Order order = new Order();
        order.setDate(LazyDate.getUnixDate());
        order.setEmployeeId(1);
        order.setFullPrice(Integer.MAX_VALUE);

        //ser order details required fields
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setAmount(1);
        orderDetails.setDishID(1);
        orderDetails.setPrice(Integer.MAX_VALUE);

        //set details for order
        OrderDetailsList detailsList = new OrderDetailsList();
        detailsList.add(orderDetails);
        order.setOrderDetails(detailsList);

        return order;
    }
}
