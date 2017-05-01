package com.shyslav.controller;


import com.happycake.HappyCakeStorage;
import com.shyslav.controller.actions.ServerActions;
import com.shyslav.defaultentityes.StringKeyValue;
import com.shyslav.defaults.ErrorCodes;
import com.shyslav.defaults.HappyCakeRequest;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.models.ServerOnlineUsers;
import com.shyslav.mysql.connectionpool.MysqlConnection;
import com.shyslav.mysql.exceptions.DBException;
import com.shyslav.utils.LazyGson;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Shyshkin Vladyslav on 27.03.2016.
 */
public class WorkThread implements Runnable {
    private static final Logger log = Logger.getLogger(WorkThread.class.getName());

    //private CookAction cook = new CookAction();
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

    public WorkThread(Socket i, HappyCakeStorage storage) {
        this.incoming = i;
        this.storage = storage;
        this.actions = new ServerActions(storage);
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
                    log.trace(" new request from client " + line);
                    parseCommand(request);
                }
            }
        } catch (IOException e) {
            log.error("Unable to start read thread " + " . " + e.getMessage(), e);
        } finally {
            try {
                delete(ServerStarApp.client);
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
        switch (request.getUrl().toLowerCase()) {
            case "login": {
                StringKeyValue keyValue = request.getObject(StringKeyValue.class);
                printWriter.println(LazyGson.toJson(actions.login(keyValue.getKey(), keyValue.getValue())));
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
            case "selectpositions": {
                printWriter.println(LazyGson.toJson(actions.selectPositions()));
                break;
            }
            case "selectorders": {
                printWriter.println(LazyGson.toJson(actions.selectOrders()));
                break;
            }
            case "deletebyid": {
                StringKeyValue keyValue = request.getObject(StringKeyValue.class);
                printWriter.println(LazyGson.toJson(actions.deleteByID(keyValue.getKey(), keyValue.getValue())));
            }
            default: {
                printWriter.println(LazyGson.toJson(new HappyCakeResponse(ErrorCodes.WROND_REQUST, "INVALID REQUEST")));
            }
        }
    }

    /**
     * Функция приема команды от клиента и правилами что делать после ее поступление
     *
     * @param splits - массив элементов команды
     */
    private void commandCheck(String[] splits) {
        //Подключение к серверу
        if (splits[0].equals("login")) {
//            Employees employees = actions.login(splits[1], splits[2]);
//            if (employees != null) {
//                    objectOut.writeObject(employees);
//                    objectOut.flush();
        } else {
//                    objectOut.writeObject("not found");
        }
    }
    //Команда на получение новостей
//            } else if (splits[0].equals("selectNews")) { +
//                ArrayList<_News> news = NewsAction.selectNews(Integer.parseInt(splits[1]));
//                if (news != null) {
//                    objectOut.writeObject(news);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение категорий
//            } else if (splits[0].equals("selectCategory")) { +
//                ArrayList<_Category> category = CategoryAction.selectCategory(Integer.parseInt(splits[1]));
//                if (category != null) {
//                    objectOut.writeObject(category);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение блюд
//            } else if (splits[0].equals("selectDish")) { +
//                ArrayList<_Dish> dish = CategoryAction.selectDish(Integer.parseInt(splits[1]));
//                if (dish != null) {
//                    objectOut.writeObject(dish);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение резервов
//            } else if (splits[0].equals("selectReservation")) { +
//                ArrayList<_Reservation> reservation = ReservationAction.selectReservation(Integer.parseInt(splits[1]));
//                if (reservation != null) {
//                    objectOut.writeObject(reservation);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение предзаказов в заказе
//            } else if (splits[0].equals("selectPreOrder")) { +
//                ArrayList<_PreOrderTable> preOrderTables = ReservationAction.selectPreOrder(Integer.parseInt(splits[1]));
//                if (preOrderTables != null) {
//                    objectOut.writeObject(preOrderTables);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение сотрудников
//            } else if (splits[0].equals("selectEmployees")) { +
//                ArrayList<Employees> employees = EmployeeAction.selectEmployees(Integer.parseInt(splits[1]));
//                if (employees != null) {
//                    objectOut.writeObject(employees);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение отзывов
//            } else if (splits[0].equals("selectReports")) { +
//                ArrayList<_Reports> reports = ReportsAction.selectReports(Integer.parseInt(splits[1]));
//                if (reports != null) {
//                    objectOut.writeObject(reports);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение кординат
//            } else if (splits[0].equals("selectCafeCoordinte")) { +
//                ArrayList<_CafeCoordinate> cafeCoordinates = EmployeeAction.selectcafeCoordinate(Integer.parseInt(splits[1]));
//                if (cafeCoordinates != null) {
//                    objectOut.writeObject(cafeCoordinates);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение вакансий
//            } else if (splits[0].equals("selectPositions")) { +
//                ArrayList<_Positions> positionses = EmployeeAction.selectPositions(Integer.parseInt(splits[1]));
//                if (positionses != null) {
//                    objectOut.writeObject(positionses);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение заказов
//            } else if (splits[0].equals("selectOrders")) { +
//                ArrayList<_Order> orderses = OrderAction.selectOrders(Integer.parseInt(splits[1]));
//                if (orderses != null) {
//                    objectOut.writeObject(orderses);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение элементов заказа
//            } else if (splits[0].equals("selectOrderList")) { +
//                ArrayList<_OrderList> orderList = OrderAction.selectorderList(Integer.parseInt(splits[1]));
//                if (orderList != null) {
//                    objectOut.writeObject(orderList);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на удаление из таблицы
//            } else if (splits[0].equals("deleteFromTable")) {
//                if (DeleteAction.delete(splits[1], splits[2]) != null) {
//                    objectOut.writeObject("done");
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение значения на апдейт
//            } else if (splits[0].equals("getValueToUpdate")) {
//                String update = UpdateAction.selectToUpdate(splits[1], splits[2], splits[3]);
//                if (update != null) {
//                    objectOut.writeObject(update);
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на обновление
//            } else if (splits[0].equals("update")) {
//                String update = UpdateAction.update(splits);
//                if (update != null) {
//                    objectOut.writeObject(update);
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на вставку
//            } else if (splits[0].equals("insert")) {
//                String insert = UpdateAction.insert(splits);
//                if (insert != null) {
//                    objectOut.writeObject(insert);
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение графиков по месяцу
//            } else if (splits[0].equals("selectGrapgMonth")) {
//                ArrayList<_GraphReport> tmp = ChartAction.selectChart(splits[1], null, null);
//                if (tmp != null) {
//                    objectOut.writeObject(tmp);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на получение графиков по дате
//            } else if (splits[0].equals("selectGrapg")) {
//                ArrayList<_GraphReport> tmp = ChartAction.selectChart(splits[1], splits[2], splits[3]);
//                if (tmp != null) {
//                    objectOut.writeObject(tmp);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на инициализацию касира
//            } else if (splits[0].equals("selectCassir")) {
//                ArrayList<LocalServerCassir> tmp = CasirAction.CasirAction();
//                if (tmp != null) {
//                    objectOut.writeObject(tmp);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на передачу на сервер обьекта
//            } else if (splits[0].equals("readObj")) {
//                try {
//                    ArrayList<_OrderList> orders = (ArrayList<_OrderList>) objectInp.readObject();
//                    System.out.println(orders.get(0).getDishName());
//                    System.out.println(splits[splits.length - 1]);
//                    String str = CasirAction.CasirAdd(orders, splits[1], splits[2]);
//                    if (orders != null) {
//                        objectOut.writeObject(str);
//                    } else {
//                        objectOut.writeObject("not found");
//                    }
//                } catch (ClassNotFoundException e) {
//                    System.out.println(e);
//                }
//                //Команда на инициализцию повара
//            } else if (splits[0].equals("getCookList")) {
//                Object tmp = cook.start();
//                if (tmp != null) {
//                    objectOut.writeObject(tmp);
//                    objectOut.flush();
//                } else {
//                    objectOut.writeObject("not found");
//                }
//                //Команда на закрытие заказа
//            } else if (splits[0].equals("compliteCookOrder")) {
//                cook.close(Integer.parseInt(splits[1]));
//                Main.sendToCook();
//            }
//}

    /**
     * Функция удаления клиента из списка подключенных к серверу
     *
     * @param client - лист клиента для удаления
     */
    public void delete(ArrayList<ServerOnlineUsers> client) {
        System.out.println("Клиент Удален");
        for (int i = 0; i < client.size(); i++) {
            if (client.get(i).getSock() == incoming) {
                client.remove(i);
            }
        }
    }
}
