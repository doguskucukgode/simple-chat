package com.mex.simplechat.client;

import com.mex.simplechat.config.AppConfig;
import com.mex.simplechat.exception.ParameterCountError;
import com.mex.simplechat.exception.ParameterStartWithError;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientApp {

    private Logger logger = Logger.getLogger(String.valueOf(ClientApp.class));

    private final String[] parameters;
    private final ParameterChecker parameterChecker;
    private Socket clientSocket;

    public ClientApp(String[] parameters) {
        this.parameters = parameters;
        parameterChecker = new ParameterChecker(parameters);
    }

    public void run() {
        try {
            String user = parameterChecker.getUser();
            clientSocket = new Socket(AppConfig.SERVER_HOST, AppConfig.SERVER_PORT);
            ClientWriter clientWriter = new ClientWriter(clientSocket);
            clientWriter.sendMessage(user);
            ClientInputManager clientInputManager = new ClientInputManager(user, clientSocket, clientWriter);
            Thread clientInputManagerThread = new Thread(clientInputManager);
            clientInputManagerThread.start();
            Thread clientReader = new Thread(new ClientReader(clientSocket, clientInputManager));
            clientReader.start();
            clientInputManagerThread.join();
            clientReader.join();
        } catch (ParameterCountError | ParameterStartWithError error) {
            logger.severe(error.getMessage());
        } catch (IOException e) {
            logger.severe("Server is not running");
        } catch (InterruptedException e) {
            logger.severe("Client input manager is interrupted");
        }

    }
}
