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
        NewsList list = client.selectNews().getObject(NewsList.class);

        //add news
        News news = new News();
        news.setName("Test");
        news.setAuthorID(1);
        news.setImageLink("Test");
        news.setTegs("Test");
        news.setText("Test");
        HappyCakeResponse response = client.addNews(news);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if news were added
        NewsList newsList = client.selectNews().getObject(NewsList.class);
        assertTrue(list.size() + 1 == newsList.size());
        News addedNews = newsList.get(newsList.size() - 1);
        assertTrue(newsList.get(newsList.size() - 1).getName().equals("Test"));

        //check if news were updated
        addedNews.setName("Test12345");
        HappyCakeResponse updateResponse = client.addNews(addedNews);
        assertTrue(updateResponse.isSuccess());

        newsList = client.selectNews().getObject(NewsList.class);
        News byID = newsList.getByID(addedNews.getId());
        assertTrue(byID.getName().equals("Test12345"));

        client.deleteNews(byID.getId());
    }

    /**
     * Add category test
     *
     * @throws Exception
     */
    @Test
    public void addCategory() throws Exception {
        ClientActions client = successLogin();

        CategoriesList list = client.selectCategories().getObject(CategoriesList.class);

        //add category
        Category category = new Category();
        category.setName("Test");
        category.setDescription("Test");
        category.setImage(new byte[]{1, 2, 3, 4, 5});

        HappyCakeResponse response = client.addCategories(category);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if category was added
        CategoriesList categoriesList = client.selectCategories().getObject(CategoriesList.class);
        assertTrue(list.size() + 1 == categoriesList.size());
        Category addedCategory = categoriesList.get(categoriesList.size() - 1);
        assertTrue(categoriesList.get(categoriesList.size() - 1).getName().equals("Test"));

        //check if category were updated
        addedCategory.setName("Test12345");
        HappyCakeResponse updateResponse = client.addCategories(addedCategory);
        assertTrue(updateResponse.isSuccess());

        categoriesList = client.selectCategories().getObject(CategoriesList.class);
        Category byID = categoriesList.getByID(addedCategory.getId());
        assertTrue(byID.getName().equals("Test12345"));

        client.deleteCategories(byID.getId());
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

        ReservationList list = client.selectReservation().getObject(ReservationList.class);

        //add reservation
        Reservation reservation = new Reservation();
        reservation.setCafeId(1);
        reservation.setClientName("Test");
        reservation.setAmountPeople(1);
        reservation.setClientPhone("test");
        reservation.setDescription("Test");

        HappyCakeResponse response = client.addReservation(reservation);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if reservation was added
        ReservationList reservationList = client.selectReservation().getObject(ReservationList.class);
        assertTrue(list.size() + 1 == reservationList.size());
        Reservation addedReservation = reservationList.get(reservationList.size() - 1);
        assertTrue(reservationList.get(reservationList.size() - 1).getClientName().equals("Test"));

        //check if reservation were updated
        addedReservation.setClientName("Test12345");
        HappyCakeResponse updateResponse = client.addReservation(addedReservation);
        assertTrue(updateResponse.isSuccess());

        reservationList = client.selectReservation().getObject(ReservationList.class);
        Reservation byID = reservationList.getByID(addedReservation.getId());
        assertTrue(byID.getClientName().equals("Test12345"));

        client.deleteReservation(byID.getId());
    }

    /**
     * Add preorder test
     *
     * @throws Exception
     */
    @Test
    public void addPreoder() throws Exception {
        ClientActions client = successLogin();

        PreOrderList list = client.selectPreOrder().getObject(PreOrderList.class);

        //add preOrder
        PreOrder preOrder = new PreOrder();
        preOrder.setDishID(1);
        preOrder.setAmount(1);
        preOrder.setReservationID(1);
        preOrder.setPrice(Integer.MAX_VALUE);

        HappyCakeResponse response = client.addPreorder(preOrder);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if preOrder was added
        PreOrderList preOrderList = client.selectPreOrder().getObject(PreOrderList.class);
        assertTrue(list.size() + 1 == preOrderList.size());
        PreOrder addedPreOrder = preOrderList.get(preOrderList.size() - 1);
        assertTrue(preOrderList.get(preOrderList.size() - 1).getPrice() == Integer.MAX_VALUE);

        //check if preOrder were updated
        addedPreOrder.setAmount(Integer.MAX_VALUE);
        HappyCakeResponse updateResponse = client.addPreorder(addedPreOrder);
        assertTrue(updateResponse.isSuccess());

        preOrderList = client.selectPreOrder().getObject(PreOrderList.class);
        PreOrder byID = preOrderList.getByID(addedPreOrder.getId());
        assertTrue(byID.getAmount() == Integer.MAX_VALUE);

        client.deletePreOrder(byID.getId());
    }

    /**
     * Add employee test
     *
     * @throws Exception
     */
    @Test
    public void addEmployee() throws Exception {
        ClientActions client = successLogin();

        EmployeesList list = client.selectEmployees().getObject(EmployeesList.class);

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


        HappyCakeResponse response = client.addEmployee(employee);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if employee was added
        EmployeesList employeesList = client.selectEmployees().getObject(EmployeesList.class);
        assertTrue(list.size() + 1 == employeesList.size());
        Employees addedEmployees = employeesList.get(employeesList.size() - 1);
        assertTrue(employeesList.get(employeesList.size() - 1).getName().equals("Test"));

        //check if employee were updated
        addedEmployees.setName("Test12345");
        HappyCakeResponse updateResponse = client.addEmployee(addedEmployees);
        assertTrue(updateResponse.isSuccess());

        employeesList = client.selectEmployees().getObject(EmployeesList.class);
        Employees byID = employeesList.getByID(addedEmployees.getId());
        assertTrue(byID.getName().equals("Test12345"));

        client.deleteEmployees(byID.getId());
    }

    /**
     * Add reports test
     *
     * @throws Exception
     */
    @Test
    public void addReports() throws Exception {
        ClientActions client = successLogin();

        ReportsList list = client.selectReports().getObject(ReportsList.class);

        //add report
        Reports reports = new Reports();
        reports.setAuthor("Test");
        reports.setMail("Test");
        reports.setPhone("Test");
        reports.setText("Test");

        HappyCakeResponse response = client.addReports(reports);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if report was added
        ReportsList reportsList = client.selectReports().getObject(ReportsList.class);
        assertTrue(list.size() + 1 == reportsList.size());
        Reports addedReports = reportsList.get(reportsList.size() - 1);
        assertTrue(reportsList.get(reportsList.size() - 1).getAuthor().equals("Test"));

        //check if report were updated
        addedReports.setAuthor("Test12345");
        HappyCakeResponse updateResponse = client.addReports(addedReports);
        assertTrue(updateResponse.isSuccess());

        reportsList = client.selectReports().getObject(ReportsList.class);
        Reports byID = reportsList.getByID(addedReports.getId());
        assertTrue(byID.getAuthor().equals("Test12345"));

        client.deleteReports(byID.getId());
    }


    /**
     * Add cafe coordinate test
     *
     * @throws Exception
     */
    @Test
    public void addCafeCoordinate() throws Exception {
        ClientActions client = successLogin();

        CafeCoordinateList list = client.selectCafeCoordinate().getObject(CafeCoordinateList.class);

        //add cafeCoordinate
        CafeCoordinate cafeCoordinate = new CafeCoordinate();
        cafeCoordinate.setAddress("Test");
        cafeCoordinate.setEmail("Test");
        cafeCoordinate.setMobilePhone("+380913030598");

        HappyCakeResponse response = client.addCafeCoordinate(cafeCoordinate);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if cafeCoordinate was added
        CafeCoordinateList cafeCoordinateList = client.selectCafeCoordinate().getObject(CafeCoordinateList.class);
        assertTrue(list.size() + 1 == cafeCoordinateList.size());
        CafeCoordinate addedCafeCoordinate = cafeCoordinateList.get(cafeCoordinateList.size() - 1);
        assertTrue(cafeCoordinateList.get(cafeCoordinateList.size() - 1).getAddress().equals("Test"));

        //check if cafeCoordinate were updated
        addedCafeCoordinate.setAddress("Test12345");
        HappyCakeResponse updateResponse = client.addCafeCoordinate(addedCafeCoordinate);
        assertTrue(updateResponse.isSuccess());

        cafeCoordinateList = client.selectCafeCoordinate().getObject(CafeCoordinateList.class);
        CafeCoordinate byID = cafeCoordinateList.getByID(addedCafeCoordinate.getId());
        assertTrue(byID.getAddress().equals("Test12345"));

        client.deleteCafeCoordinate(byID.getId());
    }


    /**
     * Add position test
     *
     * @throws Exception
     */
    @Test
    public void addPosition() throws Exception {
        ClientActions client = successLogin();

        PositionsList list = client.selectPositions().getObject(PositionsList.class);

        //add Position
        Position position = new Position();
        position.setName("Test");
        position.setSalary(Integer.MAX_VALUE);

        HappyCakeResponse response = client.addPosition(position);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if position was added
        PositionsList positionsList = client.selectPositions().getObject(PositionsList.class);
        assertTrue(list.size() + 1 == positionsList.size());
        Position addedPosition = positionsList.get(positionsList.size() - 1);
        assertTrue(positionsList.get(positionsList.size() - 1).getName().equals("Test"));

        //check if positions were updated
        addedPosition.setName("Test12345");
        HappyCakeResponse updateResponse = client.addPosition(addedPosition);
        assertTrue(updateResponse.isSuccess());

        positionsList = client.selectPositions().getObject(PositionsList.class);
        Position byID = positionsList.getByID(addedPosition.getId());
        assertTrue(byID.getName().equals("Test12345"));

        client.deletePositions(byID.getId());
    }

    /**
     * Add order test
     *
     * @throws Exception
     */
    @Test
    public void addOrder() throws Exception {
        ClientActions client = successLogin();
        OrderList list = client.selectOrders().getObject(OrderList.class);
        //add order
        Order order = generateTestOrderWithDetails();

        HappyCakeResponse response = client.addOrder(order);
        assertTrue(response.getCode() == ErrorCodes.SUCCESS);

        //check if order was added
        OrderList orderList = client.selectOrders().getObject(OrderList.class);
        assertTrue(list.size() + 1 == orderList.size());
        Order addedOrder = orderList.get(orderList.size() - 1);
        assertTrue(orderList.get(orderList.size() - 1).getFullPrice()== Integer.MAX_VALUE);

        //check if order were updated
        addedOrder.setFullPrice(Integer.MAX_VALUE);
        HappyCakeResponse updateResponse = client.addOrder(addedOrder);
        assertTrue(updateResponse.isSuccess());

        orderList = client.selectOrders().getObject(OrderList.class);
        Order byID = orderList.getByID(addedOrder.getId());
        assertTrue(byID.getFullPrice()==Integer.MAX_VALUE);

        client.deleteOrders(byID.getId());
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
