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
     * Delete dish by id
     * @param id dish id
     * @return happy cake response
     */
    HappyCakeResponse deleteDish(int id);
    /**
     * Select reservation
     *
     * @return response with reservation list
     */
    HappyCakeResponse selectReservation();

    /**
     * Delete reservation by id
     * @param id reservation id
     * @return happycake response
     */
    HappyCakeResponse deleteReservation(int id);
    /**
     * Select preorders
     *
     * @return response with preorder list
     */

    HappyCakeResponse selectPreOrder();

    /**
     * Delete preOrder by id
     * @param id preOrder id
     * @return happycake response
     */
    HappyCakeResponse deletePreOrder(int id);
    /**
     * Select employees
     *
     * @return response with employees list
     */
    HappyCakeResponse selectEmployees();

    /**
     * Delete employee by id
     * @param id employee id
     * @return happycake response
     */
    HappyCakeResponse deleteEmployees(int id);
    /**
     * Select reports
     *
     * @return response with employees list
     */
    HappyCakeResponse selectReports();

    /**
     * Delete reports by id
     * @param id report id
     * @return happycake response
     */
    HappyCakeResponse deleteReports(int id);

    /**
     * Select cafe coordinates
     *
     * @return response with cafe coordinates list
     */

    HappyCakeResponse selectCafeCoordinate();

    /**
     * Delete CafeCoordinates by id
     * @param id CafeCoordinate id
     * @return happyake response
     */
    HappyCakeResponse deleteCafeCoordinate(int id);
    /**
     * Select user positions
     *
     * @return response with position list
     */
    HappyCakeResponse selectPositions();

    /**
     * Delete positions by id
     * @param id  position id
     * @return happycake response
     */
    HappyCakeResponse deletePositions(int id);
    /**
     * Select orders
     *
     * @return response with orders list
     */

    HappyCakeResponse selectOrders();

    /**
     * Delete orders by id
     * @param id order id
     * @return happycake response
     */
    HappyCakeResponse deleteOrders(int id);
    /**
     * Delete by id from table
     *
     * @param tableName table name
     * @param id        element id
     * @return server response
     */
    HappyCakeResponse deleteByID(String tableName, String id);
}
