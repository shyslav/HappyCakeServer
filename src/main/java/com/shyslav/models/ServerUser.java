package com.shyslav.models;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Shyshkin Vladyslav on 27.03.2016.
 */
public class ServerUser {
    private int id;
    private String name;
    private String lastName;
    //----
    private InputStream inputStream = null;
    private Scanner scanner = null;
    //----
    private OutputStream outputStream = null;
    private PrintWriter printWriter = null;

    private Socket sock;
    private int positionId;

    //updates list
    private final UserUpdatesList userUpdates;

    public ServerUser(int id, String name, String lastName, InputStream inputStream, Scanner scanner, OutputStream outputStream, PrintWriter printWriter, Socket sock, int positionId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.inputStream = inputStream;
        this.scanner = scanner;
        this.outputStream = outputStream;
        this.printWriter = printWriter;
        this.sock = sock;
        this.positionId = positionId;
        this.userUpdates = new UserUpdatesList();
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

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    /**
     * Check if has updates
     *
     * @return true if has updates
     */
    public boolean isHasUpdates() {
        synchronized (userUpdates) {
            return userUpdates.size() != 0;
        }
    }

    /**
     * Add updates
     */
    public void addToUpdate(UserUpdate toUpdate) {
        synchronized (userUpdates) {
            userUpdates.add(toUpdate);
        }
    }

    /**
     * Get user updates
     */
    public UserUpdatesList getUserUpdates() {
        synchronized (userUpdates) {
            UserUpdatesList result = new UserUpdatesList();
            result.addAll(userUpdates);
            userUpdates.clear();
            return result;
        }
    }
}

