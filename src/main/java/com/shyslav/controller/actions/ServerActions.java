package com.shyslav.controller.actions;

import com.happycake.sitemodels.Employees;
import com.happycake.HappyCakeStorage;
import com.happycake.sitemodels.*;
import com.shyslav.defaults.ErrorCodes;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.mysql.exceptions.DBException;
import com.shyslav.mysql.interfaces.DBEntity;
import org.apache.log4j.Logger;

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
     * Select dish list
     *
     * @return dish list in response
     */
    @Override
    public HappyCakeResponse selectDish() {
        try {
            DishesList list = new DishesList();
            ArrayList<DBEntity> entity = storage.dishStorage.getAll();
            for (DBEntity ent : entity) {
                list.add((Dish) ent);
            }
            return new HappyCakeResponse(ErrorCodes.SUCCESS, " SUCCESS SELECT ", list);
        } catch (DBException e) {
            log.error("Unable to select news" + " . " + e.getMessage(), e);
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
}
