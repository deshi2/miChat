package com.mirosh;

public class ServerMain {

    public static void main(String[] args) {
        ServerController serverController = ServerController.getServerController();
        serverController.startServer();
    }
}
