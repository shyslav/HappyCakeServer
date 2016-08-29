package com.shyslav.controller;

import com.shyslav.models.user;
import com.shyslav.selectCommands.CookAction;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    public static ArrayList<user> client = new ArrayList<>();
    public static Properties prop = new Properties();

    /**
     * Start working with server
     * @param args начальные аргументы программы
     */
    public static void main(String[] args) {
        log.info("Server has been started");
        //System.out.println("Server has been started");
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            while (true)
            {
                //подключить клиента
                Socket incoming = serverSocket.accept();
                //создание нового потока для каждого пользователя
                Runnable runnable = new WorkThread(incoming);
                Thread tr = new Thread(runnable);
                tr.start();
            }
        }catch (IOException ex)
        {
            System.out.println(ex);
        }
    }

    /**
     * Запрос отправки обновлений на повара
     */
    public static String sendToCook()
    {
        CookAction cookAction = new CookAction();
        return sendMessageToAllUser(cookAction.get(),3);
    }

    /**
     * Функция отправки обьекта на клиента
     * @param o - обьект для отправки
     * @param toPosition - с каким ид должности пользователям отправлять
     */
    private static String sendMessageToAllUser(Object o, int toPosition){
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
                        return "sleep Error";
                    }
                    //Отправить на клиента обьект
                    Main.client.get(i).getObjectOut().writeObject(o);
                    Main.client.get(i).getObjectOut().flush();
                    return "send";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "SendError";
                }
            }
        }
        return "noUsers";
    }
}
