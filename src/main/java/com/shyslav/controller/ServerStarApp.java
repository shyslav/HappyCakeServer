package com.shyslav.controller;

import com.happycake.HappyCakeStorage;
import com.shyslav.controller.actions.ServerActions;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.models.ServerOnlineUsers;
import com.shyslav.utils.LazyGson;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerStarApp {
    public static boolean started = false;
    private static final Logger log = Logger.getLogger(ServerStarApp.class.getName());
    public ArrayList<ServerOnlineUsers> client = new ArrayList<>();
    public static HappyCakeStorage storages;
    public static ServerActions actions;

    /**
     * Start working with server
     *
     * @param args начальные аргументы программы
     */
    public static void main(String[] args) {
        ServerStarApp app = new ServerStarApp();
        app.start("/etc/start.xml");
    }

    public void start(String pathToConfigFile) {
        storages = new HappyCakeStorage(pathToConfigFile);
        actions = new ServerActions(storages);
        log.info("Server has been started");
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            started = true;
            while (true) {
                //accept new client
                Socket incoming = serverSocket.accept();
                //create new thread for client
                Runnable runnable = new WorkThread(incoming, storages, actions, this);
                Thread tr = new Thread(runnable);
                tr.start();
            }
        } catch (IOException e) {
            log.trace("Unable to accept client " + e.getMessage(), e);
        }
    }

    /**
     * Send message to all users with position id
     *
     * @param response   response to send
     * @param positionID id type of users
     */
    public void sendNotificationToUsers(HappyCakeResponse response, int positionID) {
        for (ServerOnlineUsers serverOnlineUsers : client) {
            if (serverOnlineUsers.getPositionId() == positionID) {
                serverOnlineUsers.getPrintWriter().println(LazyGson.toJson(response));
            }
        }
    }
}