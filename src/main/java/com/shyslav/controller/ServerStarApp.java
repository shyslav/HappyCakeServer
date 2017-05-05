package com.shyslav.controller;

import com.happycake.HappyCakeStorage;
import com.shyslav.models.ServerOnlineUsers;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerStarApp {
    public static boolean started = false;
    private static final Logger log = Logger.getLogger(ServerStarApp.class.getName());
    public static ArrayList<ServerOnlineUsers> client = new ArrayList<>();
    public static HappyCakeStorage storages;

    /**
     * Start working with server
     *
     * @param args начальные аргументы программы
     */
    public static void main(String[] args) {
        start("/etc/start.xml");
    }

    public static void start(String pathToConfigFile) {
        storages = new HappyCakeStorage(pathToConfigFile);
        log.info("Server has been started");
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            started = true;
            while (true) {
                //accept new client
                Socket incoming = serverSocket.accept();
                //create new thread for client
                Runnable runnable = new WorkThread(incoming, storages);
                Thread tr = new Thread(runnable);
                tr.start();
            }
        } catch (IOException e) {
            log.trace("Unable to accept client " + e.getMessage(), e);
        }
    }


    /**
     * Send message to cook
     */
//    public static String sendToCook() {
//        CookAction cookAction = new CookAction();
//        return sendMessageToAllUser(cookAction.get(), 3);
//    }

    /**
     * Send message to all users with position id
     *
     * @param o          object to send
     * @param toPosition id type of users
     */
    private static String sendMessageToAllUser(Object o, int toPosition) {
        for (int i = 0; i < ServerStarApp.client.size(); i++) {
            //Позиция юзера соответствует входящей позиции для совершения отправки
            if (ServerStarApp.client.get(i).getPositionId() == toPosition) {
                try {
                    ServerStarApp.client.get(i).getPrintWriter().println("updateCook");
                    try {
                        //Задержка перед отправкой 2 команды
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return "sleep Error";
                    }
                    //Отправить на клиента обьект
                    ServerStarApp.client.get(i).getObjectOut().writeObject(o);
                    ServerStarApp.client.get(i).getObjectOut().flush();
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