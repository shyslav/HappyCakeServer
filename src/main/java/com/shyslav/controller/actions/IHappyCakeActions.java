package com.shyslav.controller.actions;

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
     * Select dishes
     *
     * @return response with dishes list
     */
    HappyCakeResponse selectDish();

    /**
     * Select reservation
     *
     * @return response with reservation list
     */
    HappyCakeResponse selectReservation();

    /**
     * Select preorders
     *
     * @return response with preorder list
     */
    HappyCakeResponse selectPreOrder();

    /**
     * Select employees
     *
     * @return response with employees list
     */
    HappyCakeResponse selectEmployees();

    /**
     * Select reports
     *
     * @return response with employees list
     */
    HappyCakeResponse selectReports();


    /**
     * Select cafe coordinates
     *
     * @return response with cafe coordinates list
     */
    HappyCakeResponse selectCafeCoordinate();

    /**
     * Select user positions
     *
     * @return response with position list
     */
    HappyCakeResponse selectPositions();

    /**
     * Select orders
     *
     * @return response with orders list
     */
    HappyCakeResponse selectOrders();

    /**
     * Delete by id from table
     *
     * @param tableName table name
     * @param id        element id
     * @return server response
     */
    HappyCakeResponse deleteByID(String tableName, String id);
}
