package com.shyslav.controller;

import com.happycake.HappyCakeStorage;
import com.happycake.sitemodels.HappyCakeRoles;
import com.shyslav.controller.actions.ServerActions;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.models.ServerUser;
import com.shyslav.models.UserUpdate;
import com.shyslav.utils.LazyGson;
import com.shyslav.utils.LazyUUID;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerStarApp {
    public static boolean started = false;
    private static final Logger log = Logger.getLogger(ServerStarApp.class.getName());
    public ArrayList<ServerUser> client = new ArrayList<>();
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
                tr.setName("Client connection № " + LazyUUID.generateString());
                tr.start();
            }
        } catch (IOException e) {
            log.trace("Unable to accept client " + e.getMessage(), e);
        }
    }

    /**
     * Send message to all users with position id
     *
     * @param update   update to send
     * @param position type of users
     */
    public void sendNotificationToUsers(UserUpdate update, HappyCakeRoles position) {
        for (ServerUser serverOnlineUsers : client) {
            if (serverOnlineUsers.getPosition() == position) {
                serverOnlineUsers.addToUpdate(update);
            }
        }
    }

    /**
     * Send notification to all users
     *
     * @param update update to send
     */
    public void sendNotificationToAllUsers(UserUpdate update) {
        for (ServerUser serverOnlineUsers : client) {
            serverOnlineUsers.addToUpdate(update);
        }
    }
}