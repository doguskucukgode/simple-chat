package com.mex.simplechat.server;

import com.mex.simplechat.config.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class UserMessageHandler implements Runnable {

    private Logger logger = Logger.getLogger(String.valueOf(UserMessageHandler.class));
    private Socket clientSocket;
    private ServerConnectionManager server;
    private PrintWriter sender;
    private String user;

    public UserMessageHandler(Socket clientSocket, ServerConnectionManager server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = clientSocket.getOutputStream();
            sender = new PrintWriter(output, true);
            // get first line with connected username
            user = reader.readLine();
            String userIsConnectedMessage = String.format("User '%s' is connected..", user);
            server.broadcastMessage(AppConfig.SYS_INITIAL + userIsConnectedMessage, this);
            logger.info(userIsConnectedMessage);
            String receivedMessage;
            do {
                receivedMessage = reader.readLine();
                if (receivedMessage != null) {
                    String messageToBeSent = user + ":" + receivedMessage;
                    logger.info(messageToBeSent);
                    server.broadcastMessage(AppConfig.MSG_INITIAL + messageToBeSent, this);
                }
            } while (receivedMessage != null);
            String userIsGoneMessage = String.format("User '%s' is gone..", user);
            server.broadcastMessage(AppConfig.SYS_INITIAL + userIsGoneMessage, this);
            logger.info(userIsGoneMessage);
            server.removeUser(this);

        } catch (IOException e) {
            logger.severe("Connection failed");
            server.removeUser(this);
        }
    }

    public void sendMessage(String message) {
        sender.println(message);
    }
}
