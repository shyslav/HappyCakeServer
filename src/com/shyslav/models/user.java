package com.shyslav.models;

import java.net.Socket;

/**
 * Created by Shyshkin Vladyslav on 27.03.2016.
 */
public class user {
    private int id;
    private String name;
    private String lastName;
    private Socket sock;
    private int positionId;

    public user(int id, String name, String lastName, Socket sock, int positionId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.sock = sock;
        this.positionId = positionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }
}

