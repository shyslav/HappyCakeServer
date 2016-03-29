package com.shyslav.controller;

import com.shyslav.database.connector;
import com.shyslav.models.user;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static ArrayList<user> client = new ArrayList<>();
    public static Properties prop = new Properties();
    public static void main(String[] args) {
        int i = 0;
        try {
            loadProp();
            ServerSocket serverSocket = new ServerSocket(8189);
            while (true)
            {
                Socket incoming = serverSocket.accept();
                //client.add(new user(0," name", "lastName"+i, incoming, 0));
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

    public static void loadProp() {
        try(InputStream in = Main.class.getResourceAsStream("commands.properties")){
            prop.load(in);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static int getCommandArg(String nameParam)
    {
        if(prop!=null)
        {
            return Integer.parseInt(prop.getProperty(nameParam));
        }else
        {
            return 0;
        }
    }
}
