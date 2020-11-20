package com.mex.simplechat.client;

import com.mex.simplechat.util.MessageToFileWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientWriter {
    private Logger logger = Logger.getLogger(String.valueOf(ClientWriter.class));
    private PrintWriter printWriter;
    private MessageToFileWriter messageToFileWriter;
    private String user;

    public ClientWriter(Socket clientSocket, String user, MessageToFileWriter messageToFileWriter) {
        this.user = user;
        this.messageToFileWriter = messageToFileWriter;
        try {
            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            logger.severe("Cannot get output stream, " + e.getMessage());
        }
    }

    public void sendMessageAndWriteFile(String message) {
        printWriter.println(message);
        messageToFileWriter.appendFile(String.format("outgoing message => %s: %s", user, message));
    }

    public void sendMessage(String message) {
        printWriter.println(message);
    }
}
