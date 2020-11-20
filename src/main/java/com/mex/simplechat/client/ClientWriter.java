package com.mex.simplechat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientWriter {
    private Logger logger = Logger.getLogger(String.valueOf(ClientWriter.class));
    private PrintWriter printWriter;

    public ClientWriter(Socket clientSocket) {
        try {
            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            logger.severe("Cannot get output stream, " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        printWriter.println(message);
    }
}
