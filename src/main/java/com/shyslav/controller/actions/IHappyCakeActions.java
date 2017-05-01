package main.java.com.shyslav.controller.actions;

import main.java.com.shyslav.defaults.HappyCakeResponse;

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
     * Select all categories
     *
     * @return response with categories
     */
    HappyCakeResponse selectCategories();

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
}
