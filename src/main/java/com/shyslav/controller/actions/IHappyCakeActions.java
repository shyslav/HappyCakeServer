package com.shyslav.controller.actions;

import com.happycake.sitemodels.*;
import com.shyslav.defaults.HappyCakeResponse;

/**
 * @author Shyshkin Vladyslav on 01.05.17.
 */
public interface IHappyCakeActions {
    /**
     * Get employee entity by username and password
     *
     * @param username - name of user
     * @param password - password of user
     * @return happycake response
     */
    HappyCakeResponse login(String username, String password);

    /**
     * Select all news
     *
     * @return happy cake response
     */
    HappyCakeResponse selectNews();

    /**
     * Delete news by id
     *
     * @param id news id
     * @return happy cake response
     */
    HappyCakeResponse deleteNews(int id);

    /**
     * Add news
     *
     * @param news news
     * @return happycake response
     */
    HappyCakeResponse addNews(News news);

    /**
     * Select all categories
     *
     * @return response with categories
     */
    HappyCakeResponse selectCategories();

    /**
     * Delete categories
     *
     * @param id category id
     * @return happy cake response
     */
    HappyCakeResponse deleteCategories(int id);

    /**
     * Add category
     *
     * @param category category
     * @return categories
     */
    HappyCakeResponse addCategories(Category category);

    /**
     * Select dishes
     *
     * @return response with dishes list
     */
    HappyCakeResponse selectDish();

    /**
     * Add dish
     *
     * @param dish dish
     * @return happycake response
     */
    HappyCakeResponse addDish(Dish dish);

    /**
     * Select reservation
     *
     * @return response with reservation list
     */
    HappyCakeResponse selectReservation();

    /**
     * Add reservation
     *
     * @param reservation reservation entity
     * @return happycake response
     */
    HappyCakeResponse addReservation(Reservation reservation);

    /**
     * Select preorders
     *
     * @return response with preorder list
     */
    HappyCakeResponse selectPreOrder();

    /**
     * Add preorder
     *
     * @param preOrder preorder
     * @return happycake response
     */
    HappyCakeResponse addPreorder(PreOrder preOrder);

    /**
     * Select employees
     *
     * @return response with employees list
     */
    HappyCakeResponse selectEmployees();

    /**
     * Add dish
     *
     * @param employees employee
     * @return happycake response
     */
    HappyCakeResponse addEmployee(Employees employees);

    /**
     * Select reports
     *
     * @return response with employees list
     */
    HappyCakeResponse selectReports();

    /**
     * Add dish
     *
     * @param reports report entity
     * @return happycake response
     */
    HappyCakeResponse addReports(Reports reports);


    /**
     * Select cafe coordinates
     *
     * @return response with cafe coordinates list
     */
    HappyCakeResponse selectCafeCoordinate();

    /**
     * Add dish
     *
     * @param cafeCoordinate cafe coordinates
     * @return happycake response
     */
    HappyCakeResponse addCafeCoordinate(CafeCoordinate cafeCoordinate);

    /**
     * Select user positions
     *
     * @return response with position list
     */
    HappyCakeResponse selectPositions();

    /**
     * Add dish
     *
     * @param position position entity
     * @return happycake response
     */
    HappyCakeResponse addPosition(Position position);

    /**
     * Select orders
     *
     * @return response with orders list
     */
    HappyCakeResponse selectOrders();

    /**
     * Add dish
     *
     * @param order order entity
     * @return happycake response
     */
    HappyCakeResponse addOrder(Order order);

    /**
     * Delete by id from table
     *
     * @param tableName table name
     * @param id        element id
     * @return server response
     */
    HappyCakeResponse deleteByID(String tableName, String id);
}
