package com.shyslav.controller.actions;

import com.happycake.sitemodels.*;
import com.shyslav.controller.ServerClient;
import com.shyslav.defaultentityes.StringKeyValue;
import com.shyslav.defaults.HappyCakeRequest;
import com.shyslav.defaults.HappyCakeResponse;

/**
 * @author Shyshkin Vladyslav on 01.05.17.
 */
public class ClientActions implements IHappyCakeActions {
    private final ServerClient client;

    public ClientActions(ServerClient client) {
        this.client = client;
    }

    /**
     * Login to server
     *
     * @param username - name of user
     * @param password - password of user
     * @return employee in response object
     */
    @Override
    public HappyCakeResponse login(String username, String password) {
        HappyCakeRequest request = new HappyCakeRequest("login", new StringKeyValue(username, password));
        return client.writeAndRead(request);
    }

    /**
     * Select news
     *
     * @return news list in response object
     */
    @Override
    public HappyCakeResponse selectNews() {
        HappyCakeRequest request = new HappyCakeRequest("selectNews");
        return client.writeAndRead(request);
    }

    /**
     * Delete news by id
     *
     * @param id news id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteNews(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteNews", id);
        return client.writeAndRead(request);
    }

    /**
     * Add news
     *
     * @param news news
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addNews(News news) {
        HappyCakeRequest request = new HappyCakeRequest("addNews", news);
        return client.writeAndRead(request);
    }

    /**
     * Select categories
     *
     * @return categories list in response object
     */
    @Override
    public HappyCakeResponse selectCategories() {
        HappyCakeRequest request = new HappyCakeRequest("selectCategory");
        return client.writeAndRead(request);
    }

    /**
     * Delete category
     *
     * @param id category id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteCategories(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteCategories", id);
        return client.writeAndRead(request);
    }

    /**
     * Add categories
     *
     * @param category category
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addCategories(Category category) {
        HappyCakeRequest request = new HappyCakeRequest("addCategories", category);
        return client.writeAndRead(request);
    }

    /**
     * Select dish list
     *
     * @return dish list in response object
     */
    @Override
    public HappyCakeResponse selectDish() {
        HappyCakeRequest request = new HappyCakeRequest("selectDish");
        return client.writeAndRead(request);
    }

    /**
     * Add dish
     *
     * @param dish dish
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addDish(Dish dish) {
        HappyCakeRequest request = new HappyCakeRequest("addDish", dish);
        return client.writeAndRead(request);
    }

    /**
     * Select reservation list
     *
     * @return reservation list in response object
     */
    @Override
    public HappyCakeResponse selectReservation() {
        HappyCakeRequest request = new HappyCakeRequest("selectReservation");
        return client.writeAndRead(request);
    }

    /**
     * Add reservation
     *
     * @param reservation reservation entity
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addReservation(Reservation reservation) {
        HappyCakeRequest request = new HappyCakeRequest("addReservation", reservation);
        return client.writeAndRead(request);
    }

    /**
     * Select preorder list
     *
     * @return preorder list
     */
    @Override
    public HappyCakeResponse selectPreOrder() {
        HappyCakeRequest request = new HappyCakeRequest("selectPreOrder");
        return client.writeAndRead(request);
    }

    /**
     * Add preorder
     *
     * @param preOrder preorder
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addPreorder(PreOrder preOrder) {
        HappyCakeRequest request = new HappyCakeRequest("addPreorder", preOrder);
        return client.writeAndRead(request);
    }

    /**
     * Select employees list
     *
     * @return employees list
     */
    @Override
    public HappyCakeResponse selectEmployees() {
        HappyCakeRequest request = new HappyCakeRequest("selectEmployees");
        return client.writeAndRead(request);
    }

    /**
     * Add employee
     *
     * @param employees employee entity
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addEmployee(Employees employees) {
        HappyCakeRequest request = new HappyCakeRequest("addEmployee", employees);
        return client.writeAndRead(request);
    }

    /**
     * Select reports list
     *
     * @return reports list
     */
    @Override
    public HappyCakeResponse selectReports() {
        HappyCakeRequest request = new HappyCakeRequest("selectReports");
        return client.writeAndRead(request);
    }

    /**
     * Add reports
     *
     * @param reports report entity
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addReports(Reports reports) {
        HappyCakeRequest request = new HappyCakeRequest("addReports", reports);
        return client.writeAndRead(request);
    }

    /**
     * Select cafe coordinate list
     *
     * @return coordinates list
     */
    @Override
    public HappyCakeResponse selectCafeCoordinate() {
        HappyCakeRequest request = new HappyCakeRequest("selectCafeCoordinate");
        return client.writeAndRead(request);
    }

    /**
     * Add cafe coordinate
     *
     * @param cafeCoordinate cafe coordinates
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addCafeCoordinate(CafeCoordinate cafeCoordinate) {
        HappyCakeRequest request = new HappyCakeRequest("addCafeCoordinate", cafeCoordinate);
        return client.writeAndRead(request);
    }

    /**
     * Select position list
     *
     * @return position list
     */
    @Override
    public HappyCakeResponse selectPositions() {
        HappyCakeRequest request = new HappyCakeRequest("selectPositions");
        return client.writeAndRead(request);
    }

    /**
     * Add happycake position entity
     *
     * @param position position entity
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addPosition(Position position) {
        HappyCakeRequest request = new HappyCakeRequest("addPosition", position);
        return client.writeAndRead(request);
    }

    /**
     * Select orders
     *
     * @return orders list
     */
    @Override
    public HappyCakeResponse selectOrders() {
        HappyCakeRequest request = new HappyCakeRequest("selectOrders");
        return client.writeAndRead(request);
    }

    /**
     * Add order
     *
     * @param order order entity
     * @return happycake response
     */
    @Override
    public HappyCakeResponse addOrder(Order order) {
        HappyCakeRequest request = new HappyCakeRequest("addOrder", order);
        return client.writeAndRead(request);
    }

    /**
     * Delete by id
     *
     * @param tableName table name
     * @param id        element id
     * @return response
     */
    @Override
    public HappyCakeResponse deleteByID(String tableName, String id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteByID", new StringKeyValue(tableName, id));
        return client.writeAndRead(request);
    }
}