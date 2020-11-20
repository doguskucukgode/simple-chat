package com.mex.simplechat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ServerConnectionManager implements Runnable {

    private Logger logger = Logger.getLogger(String.valueOf(ServerConnectionManager.class));

    private ServerSocket serverSocket;
    private List<UserMessageHandler> clientList;

    public ServerConnectionManager(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        clientList = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                Socket client = serverSocket.accept();
                UserMessageHandler userMessageHandler = new UserMessageHandler(client, this);
                Thread userMessageHandlerThread = new Thread(userMessageHandler);
                userMessageHandlerThread.start();
                clientList.add(userMessageHandler);

            } catch (IOException e) {
                logger.severe("Connection failed");
            }
        }
    }

    void broadcastMessage(String message, UserMessageHandler notNotifiedClient) {
        for (UserMessageHandler client : clientList) {
            if (!client.equals(notNotifiedClient)) {
                client.sendMessage(message);
            }
        }
    }

    void removeUser(UserMessageHandler userMessageHandler) {
        clientList.remove(userMessageHandler);
    }

    public void disconnect() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.severe("Cannot close server socket");
        }
    }
}
