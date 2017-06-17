import com.happycake.sitemodels.*;
import com.happycake.storages.DishStorage;
import com.happycake.storages.OrderDetailsStorage;
import com.happycake.storages.OrderStorage;
import com.shyslav.mysql.connectionpool.ConnectionPool;
import com.shyslav.mysql.driver.ConnectionDriver;
import com.shyslav.mysql.exceptions.DBException;
import com.shyslav.mysql.interfaces.DBEntity;
import com.shyslav.springapp.ApplicationSpringContext;
import com.shyslav.utils.LazyCalendar;
import com.shyslav.utils.LazyRandom;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Shyshkin Vladyslav on 17.06.17.
 */
public class TestDataSaveTest {
    private static OrderStorage orderStorage;
    private static OrderDetailsStorage orderDetailsStorage;
    private static DishStorage dishStorage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ApplicationContext context;
        context = ApplicationSpringContext.getFromEmbedSource("/etc/start.xml");
        ConnectionDriver driver = (ConnectionDriver) context.getBean("database_driver");

        ConnectionPool connectionPool = new ConnectionPool(driver);

        dishStorage = new DishStorage(connectionPool);

        orderDetailsStorage = new OrderDetailsStorage(connectionPool);
        orderDetailsStorage.clear();

        orderStorage = new OrderStorage(connectionPool, orderDetailsStorage, dishStorage);
        orderStorage.clear();
    }

    @Test
    public void ordersGenerate() throws DBException {
        DishesList dishes = new DishesList();
        ArrayList<DBEntity> list = dishStorage.getAll();
        for (DBEntity dish : list) {
            dishes.add((Dish) dish);
        }
        assertTrue(dishes.size() > 5);

        Date yearFirstDay = LazyCalendar.getYearFirstDay(LazyCalendar.getCurrentDayStart());
        Date currentDay = LazyCalendar.getCurrentDayEnd();
        List<Date> daysBetweenDates = LazyCalendar.getDaysBetweenDates(yearFirstDay, currentDay);
        for (Date date : daysBetweenDates) {
            Date currentDayStart = LazyCalendar.getDateStartFromTime(date, 9, 0);
            Date currentDayEnd = LazyCalendar.getDateStartFromTime(date, 22, 0);

            int breakTimeMin = 60; //1 minutes
            int breakTimeMax = 600; // 10 minutes

            int startTime = (int) (currentDayStart.getTime() / 1000);
            int endTime = (int) (currentDayEnd.getTime() / 1000);

            int currentTime = startTime;

            while (currentTime <= endTime) {
                int randomBreak = LazyRandom.getInt(breakTimeMin, breakTimeMax);
                currentTime += randomBreak;
                System.out.println(currentTime);


                Order order = new Order();
                order.setEmployeeId(1);
                order.setComplite(true);
                order.setDate(currentTime);
                //generate order details
                int[] randomDish = LazyRandom.getUniqueInt(1, dishes.size() - 1, LazyRandom.getInt(1, 6));
                for (int dish : randomDish) {
                    OrderDetails orderDetails = generateOrderDetails(dishes.get(dish), LazyRandom.getInt(1, 4));
                    order.getOrderDetails().add(orderDetails);
                }
                order.calcFullPrice();
                orderStorage.saveOrderWithDetails(order);
            }
            System.out.println();
        }
    }

    /**
     * Generate order details
     *
     * @param dish   dish to generate
     * @param amount amount elements
     * @return order details
     */
    private OrderDetails generateOrderDetails(Dish dish, int amount) {
        OrderDetails details = new OrderDetails();
        details.setAmount(amount);
        details.setDishID(dish.getId());
        details.setPrice(amount * dish.getPrice());
        return details;
    }
}
