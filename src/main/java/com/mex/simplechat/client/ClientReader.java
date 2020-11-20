package com.mex.simplechat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientReader implements Runnable {

    private Logger logger = Logger.getLogger(String.valueOf(ClientReader.class));
    private Socket clientSocket;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private ClientInputManager clientInputManager;


    public ClientReader(Socket clientSocket, ClientInputManager clientInputManager) {
        this.clientSocket = clientSocket;
        this.clientInputManager = clientInputManager;
    }

    @Override
    public void run() {
        try {
            inputStream = clientSocket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            logger.severe("Cannot get input stream from socket, " + e.getMessage());
        }

        while (!clientInputManager.isExited()) {
            try {
                String message = bufferedReader.readLine();
                logger.fine(message);
                // TODO: Write to file

            } catch (IOException e) {
               logger.fine("Socket disconnected exiting from ClientReader");
               break;
            }
        }
    }
}
