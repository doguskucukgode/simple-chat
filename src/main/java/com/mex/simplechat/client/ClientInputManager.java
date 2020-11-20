package com.mex.simplechat.client;

import com.mex.simplechat.config.AppConfig;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientInputManager implements Runnable {

    private Logger logger = Logger.getLogger(String.valueOf(ClientInputManager.class));
    private String helpView =
            "Command Overview\n" +
                    "  /help             - This help page\n" +
                    "  /exit             - Exit from application\n" +
                    "  /send <message>   - Send message for participants\n";

    private String user;
    private boolean exited;
    private Socket clientSocket;
    private ClientWriter clientWriter;

    public ClientInputManager(String user, Socket clientSocket, ClientWriter clientWriter) {
        this.user = user;
        this.clientSocket = clientSocket;
        this.clientWriter = clientWriter;
    }

    @Override
    public void run() {
        System.out.print(String.format("Hello %s\n%s", user, helpView));

        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            System.out.print("> ");
            command = scanner.nextLine();  // Read user input

            if (command.indexOf(AppConfig.HELP_COMMAND) == 0) {
                System.out.println(helpView);
            } else if (command.indexOf(AppConfig.EXIT_COMMAND) == 0) {
                closeApplication();
                break;
            } else if (command.indexOf(AppConfig.SEND_COMMAND) == 0) {
                sendMessage(command);
            } else {
                System.out.println(String.format("Unknown command: %s\n", command));
            }
        } while (true);
        exited = true;
    }

    private void closeApplication() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.severe("Cannot close client socket, " + e.getMessage());
        }
    }

    private void sendMessage(String commandString) {
        if (commandString.split(" ").length > 1) {
            clientWriter.sendMessage(commandString.substring(AppConfig.SEND_COMMAND.length()));
        } else {
            System.out.println("Message missing: %s\n");
        }
    }

    public boolean isExited() {
        return exited;
    }
}
