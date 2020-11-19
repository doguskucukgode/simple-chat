package com.mex.simplechat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionManager implements Runnable {

    private Logger logger = Logger.getLogger(String.valueOf(ConnectionManager.class));

    private ServerSocket serverSocket;
    private List<ClientMessageHandler> clientList;

    public ConnectionManager(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        clientList = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                Socket client = serverSocket.accept();
                ClientMessageHandler clientMessageHandler = new ClientMessageHandler(client, this);
                clientList.add(clientMessageHandler);

            } catch (IOException e) {
                logger.severe("Connection failed");
            }
        }
    }

    void broadcastMessage(String message, ClientMessageHandler notNotifiedClient) {
        for (ClientMessageHandler client : clientList) {
            if (!client.equals(notNotifiedClient)) {
                client.sendMessage(message);
            }
        }
    }

    void removeUser(ClientMessageHandler clientMessageHandler) {
        clientList.remove(clientMessageHandler);
    }

    public void disconnect() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.severe("Cannot close server socket");
        }
    }
}
