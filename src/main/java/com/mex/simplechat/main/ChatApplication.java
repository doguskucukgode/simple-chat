package com.mex.simplechat.main;

import com.mex.simplechat.client.ClientApp;
import com.mex.simplechat.server.ServerApp;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * Main class to be run, it can be run as server or client
 */
@Command(
        subcommands = { ChatClient.class, ChatServer.class, CommandLine.HelpCommand.class },
        description = "Simple Chat Application, server should be started before the clients")
public class ChatApplication {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ChatApplication()).execute(args);
        System.exit(exitCode);
    }
}

@Command(name = "chat",
        description = "For users connecting to chat application")
class ChatClient implements Runnable {

    @Parameters(arity = "1..1", paramLabel = "/user:<USER>", description = "User parameter should be defined /user:<USER>")
    private String[] parameters;

    @Override
    public void run() {
        ClientApp clientApp = new ClientApp(parameters);
        clientApp.run();
    }
}

@Command(name = "server",
        description = "Starts server for chat application")
class ChatServer implements Runnable {

    @Override
    public void run() {
        ServerApp serverApp = new ServerApp();
        serverApp.run();
    }
}