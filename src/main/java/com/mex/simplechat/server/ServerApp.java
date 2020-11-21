package com.mex.simplechat.server;

import com.mex.simplechat.config.AppConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class ServerApp {

    private Logger logger = Logger.getLogger(String.valueOf(ServerApp.class));
    private ServerSocket serverSocket;

    public void run() {
        try {
            serverSocket = new ServerSocket(AppConfig.SERVER_PORT);
            ServerConnectionManager serverConnectionManager = new ServerConnectionManager(serverSocket);
            Thread connectionManagerThread = new Thread(serverConnectionManager);
            connectionManagerThread.start();
            Thread inputManager = new Thread(new ServerInputManager(serverConnectionManager));
            inputManager.start();
            inputManager.join();
        } catch (IOException | InterruptedException e) {
            logger.severe("Cannot initialize server socket");
            return;
        }
    }
}
