package main.java.com.shyslav.controller.actions;

import com.shyslav.defaultentityes.StringKeyValue;
import main.java.com.shyslav.controller.ServerClient;
import main.java.com.shyslav.defaults.HappyCakeRequest;
import main.java.com.shyslav.defaults.HappyCakeResponse;

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
     * Select position list
     *
     * @return position list
     */
    @Override
    public HappyCakeResponse selectPositions() {
        HappyCakeRequest request = new HappyCakeRequest("selectPositions");
        return client.writeAndRead(request);
    }

    @Override
    public HappyCakeResponse selectOrders() {
        HappyCakeRequest request = new HappyCakeRequest("selectOrders");
        return client.writeAndRead(request);
    }
}
