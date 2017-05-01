package com.shyslav.controller;

import com.shyslav.defaults.HappyCakeRequest;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.utils.LazyGson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Shyshkin Vladyslav on 30.04.17.
 */
public class ServerClient {
    private final Socket connection;
    private final String HOST = "127.0.0.1";
    private final int PORT = 8189;


    private final PrintWriter printWriter;
    private final Scanner scanner;

    public ServerClient() throws IOException {
        this.connection = new Socket(HOST, PORT);
        //input stream
        InputStream inputStream = connection.getInputStream();
        this.scanner = new Scanner(inputStream);
        //output stream
        OutputStream outputStream = connection.getOutputStream();
        this.printWriter = new PrintWriter(outputStream, true);
    }

    /**
     * Write request data to server
     *
     * @param request happycake request
     */
    public void write(HappyCakeRequest request) {
        printWriter.println(LazyGson.toJson(request));
    }


    /**
     * Write to server request and read answer from server
     *
     * @param request action request
     * @return server response
     */
    public HappyCakeResponse writeAndRead(HappyCakeRequest request) {
        write(request);
        return read();
    }

    /**
     * Read response from server
     */
    public HappyCakeResponse read() {
        String line = scanner.nextLine();
        return LazyGson.fromJson(line, HappyCakeResponse.class);
    }
}
