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
                printWriter.println(LazyGson.toJson(actions.addNews(news)));
                break;
            }
            case "addcategories": {
                Category category = request.getObject(Category.class);
                printWriter.println(LazyGson.toJson(actions.addCategories(category)));
                break;
            }
            case "adddish": {
                Dish dish = request.getObject(Dish.class);
                printWriter.println(LazyGson.toJson(actions.addDish(dish)));
                break;
            }
            case "addreservation": {
                Reservation reservation = request.getObject(Reservation.class);
                printWriter.println(LazyGson.toJson(actions.addReservation(reservation)));
                break;
            }
            case "addpreorder": {
                PreOrder preOrder = request.getObject(PreOrder.class);
                printWriter.println(LazyGson.toJson(actions.addPreorder(preOrder)));
                break;
            }
            case "addemployee": {
                Employees employees = request.getObject(Employees.class);
                printWriter.println(LazyGson.toJson(actions.addEmployee(employees)));
                break;
            }
            case "addreports": {
                Reports reports = request.getObject(Reports.class);
                printWriter.println(LazyGson.toJson(actions.addReports(reports)));
                break;
            }
            case "addcafecoordinate": {
                CafeCoordinate coordinate = request.getObject(CafeCoordinate.class);
                printWriter.println(LazyGson.toJson(actions.addCafeCoordinate(coordinate)));
                break;
            }
            case "addorder": {
                Order order = request.getObject(Order.class);
                printWriter.println(LazyGson.toJson(actions.addOrder(order)));
                break;
            }
            case "deletenews": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deleteNews(id)));
                break;
            }
            case "deletecategories": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deleteCategories(id)));
                break;
            }
            case "deletedish": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deleteDish(id)));
                break;
            }
            case "deletereservation": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deleteReservation(id)));
                break;
            }
            case "deletepreorder": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deletePreOrder(id)));
                break;
            }
            case "deleteemployees": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deleteEmployees(id)));
                break;
            }
            case "deletereports": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deleteReports(id)));
                break;
            }
            case "deletecafecoordinate": {
                int id = request.getObject(Integer.class);
                printWriter.println(LazyGson.toJson(actions.deleteCafeCoordinate(id)));
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
                            //generate order fo cook response
                            HappyCakeResponse response = new HappyCakeResponse(ErrorCodes.SUCCESS, storage.orderStorage.getOrdersForCook());
                            //send notification to cook
                            startApp.sendNotificationToUsers(response, HappyCakeRoles.COOK);
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
                UserUpdate userUpdate = new UserUpdate("messagetousers", message);
                startApp.sendNotificationToAllUsers(userUpdate);
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
