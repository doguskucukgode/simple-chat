package com.mex.simplechat.client;

import com.mex.simplechat.config.AppConfig;
import com.mex.simplechat.util.MessageToFileWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientReader implements Runnable {

    private final Logger logger = Logger.getLogger(String.valueOf(ClientReader.class));
    private final Socket clientSocket;
    private BufferedReader bufferedReader;
    private MessageToFileWriter messageToFileWriter;

    public ClientReader(Socket clientSocket, MessageToFileWriter messageToFileWriter) {
        this.clientSocket = clientSocket;
        this.messageToFileWriter = messageToFileWriter;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            logger.severe("Cannot get input stream from socket, " + e.getMessage());
        }

        while (true) {
            try {
                String message = bufferedReader.readLine();
                if(message == null) {
                    logger.fine("Server disconnected");
                    break;
                }
                logger.fine(message);
                if (message.indexOf(AppConfig.MSG_INITIAL) == 0) {
                    messageToFileWriter.appendFile("incoming message <= " + message.substring(AppConfig.MSG_INITIAL.length()));
                } else if (message.indexOf(AppConfig.SYS_INITIAL) == 0) {
                    messageToFileWriter.appendFile(message.substring(AppConfig.SYS_INITIAL.length()));
                }
            } catch (IOException e) {
                logger.fine("Socket disconnected exiting from ClientReader");
                break;
            }
        }
        System.exit(0);
    }
}
