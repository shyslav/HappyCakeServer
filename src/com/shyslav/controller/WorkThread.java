package com.shyslav.controller;


import com.shyslav.database.DBConnector;
import com.shyslav.models.*;
import com.shyslav.selectCommands.*;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Shyshkin Vladyslav on 27.03.2016.
 */
public class WorkThread implements Runnable {
    private CookAction cook = new CookAction();
    private Socket incoming;
    //входящее поток
    private InputStream inputStream = null;
    private Scanner scanner = null;
    //исходящее сообщение
    private OutputStream outputStream = null;
    private PrintWriter printWriter = null;
    //потоки обьектов
    private ObjectOutputStream objectOut = null;
    private ObjectInputStream objectInp = null;
    boolean done = false;

    public WorkThread(Socket i) {
        incoming = i;
    }

    @Override
    /**
     * Начало работы программы, которая принимает запрос от клиента
     */
    public void run() {
        try {
            //входящий
            inputStream = incoming.getInputStream();
            scanner = new Scanner(inputStream);
            //исходящий
            outputStream = incoming.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
            //исходящий поток данных
            objectOut = new ObjectOutputStream(incoming.getOutputStream());
            objectInp = new ObjectInputStream(incoming.getInputStream());
            while (!done && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                String[] splits = line.split("[;,:]");
                commandCheck(splits);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                delete(Main.client);
                inputStream.close();
                scanner.close();
                outputStream.close();
                printWriter.close();
                objectOut.close();
                incoming.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Функция приема команды от клиента и правилами что делать после ее поступление
     *
     * @param splits - массив элементов команды
     */
    private void commandCheck(String[] splits) {
        try {
            //Подключение к серверу
            if (splits[0].equals("login")) {
                ArrayList<employees> employees = login(splits[1], splits[2]);
                if (employees != null && employees.size() == 1) {
                    objectOut.writeObject(employees);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение новостей
            } else if (splits[0].equals("selectNews")) {
                ArrayList<news> news = NewsAction.selectNews(Integer.parseInt(splits[1]));
                if (news != null) {
                    objectOut.writeObject(news);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение категорий
            } else if (splits[0].equals("selectCategory")) {
                ArrayList<category> category = CategoryAction.selectCategory(Integer.parseInt(splits[1]));
                if (category != null) {
                    objectOut.writeObject(category);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение блюд
            } else if (splits[0].equals("selectDish")) {
                ArrayList<dish> dish = CategoryAction.selectDish(Integer.parseInt(splits[1]));
                if (dish != null) {
                    objectOut.writeObject(dish);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение резервов
            } else if (splits[0].equals("selectReservation")) {
                ArrayList<reservation> reservation = ReservationAction.selectReservation(Integer.parseInt(splits[1]));
                if (reservation != null) {
                    objectOut.writeObject(reservation);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение предзаказов в заказе
            } else if (splits[0].equals("selectPreOrder")) {
                ArrayList<preOrderTable> preOrderTables = ReservationAction.selectPreOrder(Integer.parseInt(splits[1]));
                if (preOrderTables != null) {
                    objectOut.writeObject(preOrderTables);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение сотрудников
            } else if (splits[0].equals("selectEmployees")) {
                ArrayList<employees> employees = EmployeeAction.selectEmployees(Integer.parseInt(splits[1]));
                if (employees != null) {
                    objectOut.writeObject(employees);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение отзывов
            } else if (splits[0].equals("selectReports")) {
                ArrayList<reports> reports = ReportsAction.selectReports(Integer.parseInt(splits[1]));
                if (reports != null) {
                    objectOut.writeObject(reports);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение кординат
            } else if (splits[0].equals("selectCafeCoordinte")) {
                ArrayList<cafeCoordinate> cafeCoordinates = EmployeeAction.selectcafeCoordinate(Integer.parseInt(splits[1]));
                if (cafeCoordinates != null) {
                    objectOut.writeObject(cafeCoordinates);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение вакансий
            } else if (splits[0].equals("selectPositions")) {
                ArrayList<positions> positionses = EmployeeAction.selectPositions(Integer.parseInt(splits[1]));
                if (positionses != null) {
                    objectOut.writeObject(positionses);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение заказов
            } else if (splits[0].equals("selectOrders")) {
                ArrayList<orders> orderses = OrderAction.selectOrders(Integer.parseInt(splits[1]));
                if (orderses != null) {
                    objectOut.writeObject(orderses);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение элементов заказа
            } else if (splits[0].equals("selectOrderList")) {
                ArrayList<orderList> orderList = OrderAction.selectorderList(Integer.parseInt(splits[1]));
                if (orderList != null) {
                    objectOut.writeObject(orderList);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на удаление из таблицы
            } else if (splits[0].equals("deleteFromTable")) {
                if (DeleteAction.delete(splits[1], splits[2]) != null) {
                    objectOut.writeObject("done");
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение значения на апдейт
            } else if (splits[0].equals("getValueToUpdate")) {
                String update = UpdateAction.selectToUpdate(splits[1], splits[2], splits[3]);
                if (update != null) {
                    objectOut.writeObject(update);
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на обновление
            } else if (splits[0].equals("update")) {
                String update = UpdateAction.update(splits);
                if (update != null) {
                    objectOut.writeObject(update);
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на вставку
            } else if (splits[0].equals("insert")) {
                String insert = UpdateAction.insert(splits);
                if (insert != null) {
                    objectOut.writeObject(insert);
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение графиков по месяцу
            } else if (splits[0].equals("selectGrapgMonth")) {
                ArrayList<ReportsGraph> tmp = ChartAction.selectChart(splits[1], null, null);
                if (tmp != null) {
                    objectOut.writeObject(tmp);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на получение графиков по дате
            } else if (splits[0].equals("selectGrapg")) {
                ArrayList<ReportsGraph> tmp = ChartAction.selectChart(splits[1], splits[2], splits[3]);
                if (tmp != null) {
                    objectOut.writeObject(tmp);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на инициализацию касира
            } else if (splits[0].equals("selectCassir")) {
                ArrayList<_Cassir> tmp = CasirAction.CasirAction();
                if (tmp != null) {
                    objectOut.writeObject(tmp);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на передачу на сервер обьекта
            } else if (splits[0].equals("readObj")) {
                try {
                    ArrayList<orderList> orders = (ArrayList<orderList>) objectInp.readObject();
                    System.out.println(orders.get(0).getDishName());
                    System.out.println(splits[splits.length - 1]);
                    String str = CasirAction.CasirAdd(orders, splits[1], splits[2]);
                    if (orders != null) {
                        objectOut.writeObject(str);
                    } else {
                        objectOut.writeObject("not found");
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                }
                //Команда на инициализцию повара
            } else if (splits[0].equals("getCookList")) {
                Object tmp = cook.start();
                if (tmp != null) {
                    objectOut.writeObject(tmp);
                    objectOut.flush();
                } else {
                    objectOut.writeObject("not found");
                }
                //Команда на закрытие заказа
            } else if (splits[0].equals("compliteCookOrder")) {
                cook.close(Integer.parseInt(splits[1]));
                Main.sendToCook();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Функция удаления клиента из списка подключенных к серверу
     *
     * @param client - лист клиента для удаления
     */
    public void delete(ArrayList<user> client) {
        System.out.println("Клиент Удален");
        for (int i = 0; i < client.size(); i++) {
            if (client.get(i).getSock() == incoming) {
                client.remove(i);
            }
        }
    }

    /**
     * Функция логина и доступа к серверу
     *
     * @param username - имя пользователя
     * @param password - пароль пользователя
     * @return Лист сотрудника который успешно подключился к серверу
     */
    public ArrayList<employees> login(String username, String password) {
        try (Connection conn = DBConnector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery("select id, positionsID, cafeID, name, lastname, adress, birthdayDay, elogin, epassword from employees " +
                    " where elogin='" + username + "' and epassword='" + password + "'")) {
                if (resultSet.next()) {
                    //Добавить в лист текущих клиентов подключившегося клиента с всеми параметрами для работы с ним
                    Main.client.add(new user(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("lastname"),
                            inputStream, scanner, outputStream, printWriter, objectOut, objectInp,
                            incoming, resultSet.getInt("positionsID")));
                    ArrayList<employees> empl = new ArrayList<>();
                    //Заполнить лист для передачи на клиента его всех данных
                    empl.add(new employees(resultSet.getInt("id"), resultSet.getInt("positionsID"), resultSet.getInt("cafeID"),
                            resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getString("adress"), resultSet.getDate("birthdayDay"),
                            resultSet.getString("elogin"), resultSet.getString("epassword")));
                    return empl;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
