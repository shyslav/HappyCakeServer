package com.shyslav.controller;

import com.shyslav.database.connector;
import com.shyslav.models.user;
import com.shyslav.selectCommands.CookAction;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static ArrayList<user> client = new ArrayList<>();
    public static Properties prop = new Properties();

    /**
     * Начало работы сервера
     * @param args начальные аргументы программы
     */
    public static void main(String[] args) {
        int i = 0;
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            while (true)
            {
                //подключить клиента
                Socket incoming = serverSocket.accept();
                //создание нового потока для каждого пользователя
                Runnable runnable = new work(incoming);
                Thread tr = new Thread(runnable);
                tr.start();
                i++;
            }
        }catch (IOException ex)
        {
            System.out.println(ex);
        }
    }

    /**
     * Запрос отправки обновлений на повара
     */
    public static void sendToCook()
    {
        CookAction cookAction = new CookAction();
        sendMessageToAllUser(cookAction.get(),3);
    }

    /**
     * Функция отправки обьекта на клиента
     * @param o - обьект для отправки
     * @param toPosition - с каким ид должности пользователям отправлять
     */
    private static void sendMessageToAllUser(Object o, int toPosition){
        for (int i = 0; i < Main.client.size(); i++) {
            //Позиция юзера соответствует входящей позиции для совершения отправки
            if(Main.client.get(i).getPositionId()==toPosition)
            {
                try {
                    Main.client.get(i).getPrintWriter().println("updateCook");
                    try {
                        //Задержка перед отправкой 2 команды
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Отправить на клиента обьект
                    Main.client.get(i).getObjectOut().writeObject(o);
                    Main.client.get(i).getObjectOut().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
