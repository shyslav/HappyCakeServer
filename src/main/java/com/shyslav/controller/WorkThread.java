package com.shyslav.controller;


import com.happycake.HappyCakeStorage;
import com.happycake.sitemodels.*;
import com.shyslav.controller.actions.ServerActions;
import com.shyslav.defaultentityes.IntegerKeyValue;
import com.shyslav.defaultentityes.StringKeyValue;
import com.shyslav.defaults.ErrorCodes;
import com.shyslav.defaults.HappyCakeRequest;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.models.ServerUser;
import com.shyslav.models.UserUpdate;
import com.shyslav.models.UserUpdatesList;
import com.shyslav.mysql.exceptions.DBException;
import com.shyslav.utils.LazyGson;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Shyshkin Vladyslav on 27.03.2016.
 */
public class WorkThread implements Runnable {
    private static final Logger log = Logger.getLogger(WorkThread.class.getName());

    private Socket incoming;
    //input stream
    private InputStream inputStream = null;
    private Scanner scanner = null;
    //output stream
    private OutputStream outputStream = null;
    private PrintWriter printWriter = null;

    boolean done = false;

    //-----
    private final HappyCakeStorage storage;
    private final ServerActions actions;
    private final ServerStarApp startApp;
    private ServerUser user;

    public WorkThread(Socket i, HappyCakeStorage storage, ServerActions actions, ServerStarApp startApp) {
        this.incoming = i;
        this.storage = storage;
        this.actions = actions;
        this.startApp = startApp;
    }

    @Override
    public void run() {
        try {
            //initialize input stream
            inputStream = incoming.getInputStream();
            scanner = new Scanner(inputStream);
            //initialize output stream
            outputStream = incoming.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
            while (!done) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    HappyCakeRequest request = LazyGson.fromJson(line, HappyCakeRequest.class);
                    parseCommand(request);
                } else if (inputStream.read() == -1) {
                    log.trace("socket is terminated");
                    done = true;
                }
            }
        } catch (IOException e) {
            log.error("Unable to start read thread " + " . " + e.getMessage(), e);
        } finally {
            try {
                delete(startApp.client);
                inputStream.close();
                scanner.close();
                outputStream.close();
                printWriter.close();
                incoming.close();
            } catch (IOException e) {
                log.error(" Unable to delete user " + e.getMessage(), e);
            }
        }
    }

    /**
     * Parse command
     *
     * @param request request
     */
    private void parseCommand(HappyCakeRequest request) {
        log.trace(" new request from client " + (user != null ? user.getId() : "") + " " + request.getUrl());
        switch (request.getUrl().toLowerCase()) {
            case "login": {
                StringKeyValue keyValue = request.getObject(StringKeyValue.class);
                HappyCakeResponse response = actions.login(keyValue.getKey(), keyValue.getValue());
                if (response.isSuccess()) {
                    Employees employees = response.getObject(Employees.class);
                    this.user = new ServerUser(
                            employees.getId(),
                            employees.getName(),
                            employees.getLastname(),
                            inputStream,
                            scanner,
                            outputStream,
                            printWriter,
                            incoming,
                            employees.getPosition()
                    );
                    startApp.client.add(this.user);
                }
                printWriter.println(LazyGson.toJson(response));
                break;
            }
            case "selectnews": {
                printWriter.println(LazyGson.toJson(actions.selectNews()));
                break;
            }
            case "selectcategory": {
                printWriter.println(LazyGson.toJson(actions.selectCategories()));
                break;
            }
            case "selectdish": {
                printWriter.println(LazyGson.toJson(actions.selectDish()));
                break;
            }
            case "selectreservation": {
                printWriter.println(LazyGson.toJson(actions.selectReservation()));
                break;
            }
            case "selectpreorder": {
                printWriter.println(LazyGson.toJson(actions.selectPreOrder()));
                break;
            }
            case "selectemployees": {
                printWriter.println(LazyGson.toJson(actions.selectEmployees()));
                break;
            }
            case "selectreports": {
                printWriter.println(LazyGson.toJson(actions.selectReports()));
                break;
            }
            case "selectcafecoordinate": {
                printWriter.println(LazyGson.toJson(actions.selectCafeCoordinate()));
                break;
            }
            case "selectorders": {
                printWriter.println(LazyGson.toJson(actions.selectOrders()));
                break;
            }
            case "selectordersforcook": {
                printWriter.println(LazyGson.toJson(actions.selectOrderForCook()));
                break;
            }
            case "deletebyid": {
                StringKeyValue keyValue = request.getObject(StringKeyValue.class);
                printWriter.println(LazyGson.toJson(actions.deleteByID(keyValue.getKey(), keyValue.getValue())));
                break;
            }
            case "addnews": {
                News news = request.getObject(News.class);
                HappyCakeResponse response = actions.addNews(news);
                printWriter.println(LazyGson.toJson(response));
                //news notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_NEWS, "news was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "addcategories": {
                Category category = request.getObject(Category.class);
                HappyCakeResponse response = actions.addCategories(category);
                printWriter.println(LazyGson.toJson(response));
                //category notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_CATEGORIES, "category was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.EMPLOYEES, user);
                }
                break;
            }
            case "adddish": {
                Dish dish = request.getObject(Dish.class);
                HappyCakeResponse response = actions.addDish(dish);
                printWriter.println(LazyGson.toJson(response));
                //dish notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_DISHES, "dish was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.EMPLOYEES, user);
                }
                break;
            }
            case "addreservation": {
                Reservation reservation = request.getObject(Reservation.class);
                HappyCakeResponse response = actions.addReservation(reservation);
                printWriter.println(LazyGson.toJson(response));
                //reservation notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_RESERVATION, "reservation was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "addpreorder": {
                PreOrder preOrder = request.getObject(PreOrder.class);
                HappyCakeResponse response = actions.addPreorder(preOrder);
                printWriter.println(LazyGson.toJson(response));
                //preorder notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_PREORDER, "preorder was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "addemployee": {
                Employees employees = request.getObject(Employees.class);
                HappyCakeResponse response = actions.addEmployee(employees);
                printWriter.println(LazyGson.toJson(response));
                //employee notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_EMPLOYEES, "employee was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "addreports": {
                Reports reports = request.getObject(Reports.class);
                HappyCakeResponse response = actions.addReports(reports);
                printWriter.println(LazyGson.toJson(response));
                //reports notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_REPORTS, "reports was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "addcafecoordinate": {
                CafeCoordinate coordinate = request.getObject(CafeCoordinate.class);
                HappyCakeResponse response = actions.addCafeCoordinate(coordinate);
                printWriter.println(LazyGson.toJson(response));
                //cafe coordinate notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_CAFECOORDINATE, "reports was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "addorder": {
                Order order = request.getObject(Order.class);
                HappyCakeResponse response = actions.addOrder(order);
                printWriter.println(LazyGson.toJson(response));
                //order notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_ORDERS, "orders was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "deletenews": {
                int id = request.getObject(Integer.class);
                HappyCakeResponse response = actions.deleteNews(id);
                printWriter.println(LazyGson.toJson(response));
                //news notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_NEWS, "news was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "deletecategories": {
                int id = request.getObject(Integer.class);
                HappyCakeResponse response = actions.deleteCategories(id);
                printWriter.println(LazyGson.toJson(response));
                //category notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_CATEGORIES, "category was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.EMPLOYEES, user);
                }
                break;
            }
            case "deletedish": {
                int id = request.getObject(Integer.class);
                HappyCakeResponse response = actions.deleteDish(id);
                printWriter.println(LazyGson.toJson(response));
                //dish notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_DISHES, "dish was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.EMPLOYEES, user);
                }
                break;
            }
            case "deletereservation": {
                int id = request.getObject(Integer.class);
                HappyCakeResponse response = actions.deleteReservation(id);
                printWriter.println(LazyGson.toJson(response));
                //reservation notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_RESERVATION, "reservation was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "deletepreorder": {
                int id = request.getObject(Integer.class);
                HappyCakeResponse response = actions.deletePreOrder(id);
                printWriter.println(LazyGson.toJson(response));
                //preorder notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_PREORDER, "preorder was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "deleteemployees": {
                int id = request.getObject(Integer.class);
                HappyCakeResponse response = actions.deleteEmployees(id);
                printWriter.println(LazyGson.toJson(response));
                //employee notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_EMPLOYEES, "employee was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "deletereports": {
                int id = request.getObject(Integer.class);
                HappyCakeResponse response = actions.deleteReports(id);
                printWriter.println(LazyGson.toJson(response));
                //reports notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_REPORTS, "reports was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "deletecafecoordinate": {
                int id = request.getObject(Integer.class);
                HappyCakeResponse response = actions.deleteCafeCoordinate(id);
                printWriter.println(LazyGson.toJson(response));
                //cafe coordinate notification to users
                if (response.isSuccess()) {
                    UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.UPDATE_CAFECOORDINATE, "reports was changed");
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                }
                break;
            }
            case "deleteorders": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deleteOrders(id)));
                break;
            }
            case "saveorderwithdetails": {
                Order order = request.getObject(Order.class);
                printWriter.println(LazyGson.toJson(actions.saveOrderWithDetails(order)));
                //send notification to cook about new order
                //check if order new
                if (order.getId() == 0) {
                    try {
                        //check if order need to cook
                        if (storage.orderStorage.isNeedCookOrder(order)) {
                            //generate update orders notification
                            UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.COOK_UPDATE_ORDERS, "update orders notification");
                            //send notification to cook
                            startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.COOK, user);
                            startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.ADMIN, user);
                            startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.EMPLOYEES, user);
                        }
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "getsalesforperiod": {
                IntegerKeyValue integerKeyValue = request.getObject(IntegerKeyValue.class);
                printWriter.println(LazyGson.toJson(actions.getSalesForPeriod(integerKeyValue.getKey(), integerKeyValue.getValue())));
                break;
            }
            case "getdatesalesforperiod": {
                IntegerKeyValue integerKeyValue = request.getObject(IntegerKeyValue.class);
                printWriter.println(LazyGson.toJson(actions.getDateSalesForPeriod(integerKeyValue.getKey(), integerKeyValue.getValue())));
                break;
            }
            case "getdataforimtalgo": {
                int[] dishIDS = request.getObject(int[].class);
                printWriter.println(LazyGson.toJson(actions.getDataForIMTAlgo(dishIDS)));
                break;
            }
            case "anyupdates": {
                if (user.isHasUpdates()) {
                    UserUpdatesList userUpdates = user.popAllUserUpdates();
                    HappyCakeResponse response = new HappyCakeResponse(ErrorCodes.SUCCESS, "found updates", userUpdates);
                    printWriter.println(LazyGson.toJson(response));
                } else {
                    HappyCakeResponse response = new HappyCakeResponse(ErrorCodes.EMPTY, "updates not found");
                    printWriter.println(LazyGson.toJson(response));
                }
                break;
            }
            case "messagetousers": {
                String[] arr = request.getObject(String[].class);
                if (arr.length != 2) {
                    log.error("Message to user have got wrong arguments");
                    break;
                }
                String role = arr[0];
                String message = arr[1];
                UserUpdate userUpdate = new UserUpdate(HappyCakeNotifications.MESSAGE_TO_USERS, message);
                if (role.equalsIgnoreCase("all")) {
                    startApp.sendNotificationToAllUsers(userUpdate, user);
                } else {
                    startApp.sendNotificationToUsers(userUpdate, HappyCakeRoles.valueOf(role), user);
                }
                break;
            }
            default: {
                printWriter.println(LazyGson.toJson(new HappyCakeResponse(ErrorCodes.WROND_REQUST, "INVALID REQUEST")));
            }
        }
    }

    /**
     * Функция удаления клиента из списка подключенных к серверу
     *
     * @param client - лист клиента для удаления
     */
    public void delete(ArrayList<ServerUser> client) {
        log.trace("Remove client");
        for (int i = 0; i < client.size(); i++) {
            if (client.get(i).getSock() == incoming) {
                client.remove(i);
            }
        }
    }
}
