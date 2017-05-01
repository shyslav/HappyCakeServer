package com.shyslav.controller.actions;

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
     * Delete dish by id
     * @param id dish id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteDish(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteDish", id);
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
     * Delete reservation by id
     * @param id reservation id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteReservation(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteReservation", id);
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
     * Delete preOrder by id
     * @param id preOrder id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deletePreOrder(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deletePreOrder", id);
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
     * Delete employee by id
     * @param id employee id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteEmployees(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteEmployees", id);
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
     * Delete report by id
     * @param id report id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteReports(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteReports", id);
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
     * Delete cafeCoordinate by id
     * @param id CafeCoordinate id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteCafeCoordinate(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteCafeCoordinate", id);
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
     * Delete position by id
     * @param id  position id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deletePositions(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deletePositions", id);
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
     * Delete order by id
     * @param id order id
     * @return happycake response
     */
    @Override
    public HappyCakeResponse deleteOrders(int id) {
        HappyCakeRequest request = new HappyCakeRequest("deleteOrders", id);
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
