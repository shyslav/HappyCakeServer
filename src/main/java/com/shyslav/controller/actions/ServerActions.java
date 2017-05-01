package com.shyslav.controller.actions;

import com.happycake.sitemodels.Employees;
import com.happycake.HappyCakeStorage;
import com.happycake.sitemodels.*;
import com.shyslav.defaults.ErrorCodes;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.mysql.connectionpool.MysqlConnection;
import com.shyslav.mysql.exceptions.DBException;
import com.shyslav.mysql.interfaces.DBEntity;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Shyshkin Vladyslav on 01.05.17.
 */
public class ServerActions implements IHappyCakeActions {
    private static final Logger log = Logger.getLogger(ServerActions.class.getName());
    private final HappyCakeStorage storage;


    public ServerActions(HappyCakeStorage storage) {
        this.storage = storage;
    }

    /**
     * Login new user
     *
     * @param username - name of user
     * @param password - password of user
     * @return employee
     */
    @Override
    public HappyCakeResponse login(String username, String password) {
        try {
            Employees login = storage.employeesStorage.getEmployeeByLoginAndPassword(username, password);
            if (login == null) {
                return new HappyCakeResponse(ErrorCodes.ACCESS_DENIED, "wrong username or password");
            } else {
                return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS LOGIN ", login);
            }
        } catch (DBException e) {
            log.error("Unable to login username by username = " + username + " password = " + password + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select news
     *
     * @return news list
     */
    @Override
    public HappyCakeResponse selectNews() {
        try {
            NewsList list = new NewsList();
            ArrayList<DBEntity> entity = storage.newsStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((News) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete news by id
     *
     * @param id news id
     * @return happy cake response
     */
    @Override
    public HappyCakeResponse deleteNews(int id) {
        try {
            storage.newsStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete news with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select categories
     *
     * @return categories list in response
     */
    @Override
    public HappyCakeResponse selectCategories() {
        try {
            CategoriesList list = new CategoriesList();
            ArrayList<DBEntity> entity = storage.categoryStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((Category) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete category by id
     *
     * @param id category id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteCategories(int id) {
        try {
            storage.categoryStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete category with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select dish list
     *
     * @return dish list in response
     */
    @Override
    public HappyCakeResponse selectDish() {
        try {
            //load hot prices
            HotPriceList hotPrices = new HotPriceList();
            storage.hotPriceStorage.getAll().forEach(e -> hotPrices.add((HotPrice) e));
            //load dishes
            DishesList list = new DishesList();
            ArrayList<DBEntity> entity = storage.dishStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((Dish) ent);
            }
            //set discount for dishes
            list.loadDiscount(hotPrices);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * delete dish by id
     * @param id dish id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteDish(int id) {
        try {
            storage.dishStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete dish with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select reservation list
     *
     * @return reservation list in response
     */
    @Override
    public HappyCakeResponse selectReservation() {
        try {
            ReservationList list = new ReservationList();
            ArrayList<DBEntity> entity = storage.reservationStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((Reservation) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * delete reservation by id
     * @param id reservation id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteReservation(int id) {
        try {
            storage.reservationStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete reservation with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select pre orders
     *
     * @return preorders list in response
     */
    @Override
    public HappyCakeResponse selectPreOrder() {
        try {
            PreOrderList list = new PreOrderList();
            ArrayList<DBEntity> entity = storage.preOrderStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((PreOrder) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete preOrder by id
     * @param id preOrder id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deletePreOrder(int id) {
        try {
            storage.preOrderStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete preOrder with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select employees list
     *
     * @return employees list in response object
     */
    @Override
    public HappyCakeResponse selectEmployees() {
        try {
            EmployeesList list = new EmployeesList();
            ArrayList<DBEntity> entity = storage.employeesStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((Employees) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete employee by id
     * @param id employee id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteEmployees(int id) {
        try {
            storage.employeesStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete employee with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select reports list
     *
     * @return reports list in response object
     */
    @Override
    public HappyCakeResponse selectReports() {
        try {
            ReportsList list = new ReportsList();
            ArrayList<DBEntity> entity = storage.reportsStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((Reports) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete report by if
     * @param id report id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteReports(int id) {
        try {
            storage.reportsStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete report with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select reports list
     *
     * @return reports list in response object
     */
    @Override
    public HappyCakeResponse selectCafeCoordinate() {
        try {
            CafeCoordinateList list = new CafeCoordinateList();
            ArrayList<DBEntity> entity = storage.cafeCoordinate.getAll();
            for (DBEntity ent : entity) {
                list.add((CafeCoordinate) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete CafeCoordinate by id
     * @param id CafeCoordinate id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteCafeCoordinate(int id) {
        try {
            storage.cafeCoordinate.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete cafeCoordinate with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select positions list
     *
     * @return position list in response object
     */
    @Override
    public HappyCakeResponse selectPositions() {
        try {
            PositionsList list = new PositionsList();
            ArrayList<DBEntity> entity = storage.positionStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((Position) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete position by id
     * @param id  position id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deletePositions(int id) {
        try {
            storage.positionStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete position with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Select orders list
     *
     * @return orders list
     */
    @Override
    public HappyCakeResponse selectOrders() {
        try {
            OrderList list = new OrderList();
            ArrayList<DBEntity> entity = storage.orderStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((Order) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", storage.orderStorage.load(list));
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete order by id
     * @param id order id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteOrders(int id) {
        try {
            storage.orderStorage.deleteByID(id);
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " DELETE SUCCESS ");
        } catch (DBException e) {
            log.error("Unable to delete order with id " + id + " . " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.INTERNAL_ERROR, " INTERNAL ERROR ");
        }
    }

    /**
     * Delete from table by id
     *
     * @param tableName table name
     * @param id        element id
     * @return
     */
    @Override
    public HappyCakeResponse deleteByID(String tableName, String id) {
        try (MysqlConnection connection = storage.getPool().getConnection()) {
            connection.executeQuery("DELETE FROM " + tableName + " where id = " + id);
        } catch (DBException e) {
            log.error(" Unable to get mysql connection " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.WROND_REQUST, " Unable to get mysql connection " + e.getMessage());
        } catch (SQLException e) {
            log.error(" Unable to execute delete by id command " + e.getMessage(), e);
            return new HappyCakeResponse(ErrorCodes.WROND_REQUST, " Unable to execute delete by id command " + e.getMessage());
        }
        return new HappyCakeResponse(ErrorCodes.SUCCESS, "SUCCESS DELETE");
    }
}
