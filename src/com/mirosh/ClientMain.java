package com.mirosh;

/**
 * Just main class of client application. Starts client application.
 */
public class ClientMain {

    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        clientController.startClient();
    }

}
