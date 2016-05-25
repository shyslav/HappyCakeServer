package com.shyslav.models;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Shyshkin Vladyslav on 27.03.2016.
 */
public class user {
    private int id;
    private String name;
    private String lastName;
    //входящее поток
    private InputStream inputStream = null;
    private Scanner scanner = null;
    //исходящее сообщение
    private OutputStream outputStream = null;
    private PrintWriter printWriter = null;
    //потоки обьектов
    private ObjectOutputStream objectOut = null;
    private ObjectInputStream objectInp = null;

    private Socket sock;
    private int positionId;

    public user(int id, String name, String lastName, InputStream inputStream, Scanner scanner, OutputStream outputStream, PrintWriter printWriter, ObjectOutputStream objectOut, ObjectInputStream objectInp, Socket sock, int positionId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.inputStream = inputStream;
        this.scanner = scanner;
        this.outputStream = outputStream;
        this.printWriter = printWriter;
        this.objectOut = objectOut;
        this.objectInp = objectInp;
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

    public ObjectOutputStream getObjectOut() {
        return objectOut;
    }

    public void setObjectOut(ObjectOutputStream objectOut) {
        this.objectOut = objectOut;
    }

    public ObjectInputStream getObjectInp() {
        return objectInp;
    }

    public void setObjectInp(ObjectInputStream objectInp) {
        this.objectInp = objectInp;
    }
}

