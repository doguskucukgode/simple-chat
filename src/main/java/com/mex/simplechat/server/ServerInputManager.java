package com.mex.simplechat.server;

import com.mex.simplechat.config.AppConfig;

import java.util.Scanner;

public class ServerInputManager implements Runnable {

    private String helpView =
            "Command Overview\n" +
            "  /help             - This help page\n" +
            "  /exit             - Exit from application\n";

    private ConnectionManager connectionManager;
    public ServerInputManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void run() {
        System.out.print("Server is started\n" + helpView);

        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        String command;
        do {
            System.out.print("> ");
            command = scanner.nextLine();  // Read user input
            if (command.equals(AppConfig.HELP_COMMAND)) {
                System.out.println(helpView);
            } else if (command.equals(AppConfig.EXIT_COMMAND)) {
                connectionManager.disconnect();
            } else {
                System.out.println(String.format("Unknown command: %s\n", command));
            }
        } while (!command.equals(AppConfig.EXIT_COMMAND));

    }
}
