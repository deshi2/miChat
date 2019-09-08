package com.mirosh;

/**
 * Main calss of server application. Just creates new instance of server.
 */
public class ServerMain {

    public static void main(String[] args) {
        ServerController serverController = ServerController.getServerController();
        serverController.startServer();
    }
}