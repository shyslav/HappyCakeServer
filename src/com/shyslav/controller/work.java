package com.shyslav.controller;


import com.shyslav.database.connector;
import com.shyslav.models.employees;
import com.shyslav.models.user;
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
public class work implements Runnable {
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

    public work(Socket i) {
        incoming = i;
    }

    @Override
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
            while (!done && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                String[] splits = line.split("[;,:]");
                if(splits[0].equals("login"))
                {
                    ArrayList<employees> employees = login(splits[1],splits[2]);
                    if(employees!=null&&employees.size()==1) {
                        objectOut.writeObject(employees);
                        objectOut.flush();
                    }else
                    {
                        objectOut.writeObject("not found");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                //delete(Main.client);
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

    public void sendMessageToAllUser(ArrayList<user> client) throws IOException {
        for (int i = 0; i < client.size(); i++) {
            OutputStream outputSt = client.get(i).getSock().getOutputStream();
            PrintWriter out = new PrintWriter(outputSt, true);
            out.println("Client" + client.get(i).getLastName() + i + " disconected");
        }
    }

    public void delete(ArrayList<user> client) {
        System.out.println("Клиент Удален");
        for (int i = 0; i < client.size(); i++) {
            if (client.get(i).getSock() == incoming) {
                client.remove(i);
            }
        }
    }

    public ArrayList<employees> login(String username, String password) {
        try (Connection conn = connector.connect()) {
            Statement statement = conn.createStatement();
            try (ResultSet resultSet = statement.executeQuery("select id, positionsID, cafeID, name, lastname, adress, birthdayDay, elogin, epassword from employees " +
                    " where elogin='" + username + "' and epassword='" + password + "'")) {
                if (resultSet.next()) {
                    Main.client.add(new user(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("lastname"),
                            incoming, resultSet.getInt("positionsID")));
                    ArrayList<employees> empl = new ArrayList<>();
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
