package com.mirosh;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Rules all things that happens at the server side, i.e. creates new thread after client connected etc.
 * The idea is to creates new thread for each client.
 */
public class ServerController {

    private static ServerController miServerController;

    private ServerSocket serverSocket;
    private MessageController messageController;

    private Thread sendingThread;

    private MessagesServerSender messagesServerSender;
    private ArrayList<ServerConnector> clientConnections; // All current client connections.
    private ArrayListLock messages;  // array of Hash map of all client messages with elements: {"userName:message"}

    /**
     * We can have only one server so singleton pattern used.
     * @return One and only instance of this class.
     */
    public static ServerController getServerController() {
        if (miServerController == null) {
            miServerController = new ServerController();
        }
        return miServerController;
    }

    private ArrayList<ServerConnector> getClientConnections() {
        return this.clientConnections;
    }

    private ArrayListLock getMessages() {
        return this.messages;
    }

    private void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    private void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    private MessageController getMessageController() {
        return this.messageController;
    }

    private MessagesServerSender getMessagesServerSender() {
        return this.messagesServerSender;
    }

    private void initializeMessageServerSender() {
        this.messagesServerSender = new MessagesServerSender(getClientConnections(), getMessageController(), getMessages());
    }
    private void initializeClientConnections() {
        this.clientConnections = new ArrayList<ServerConnector>();
    }

    private void initializeMessages() { this.messages = new ArrayListLock(); }

    private Thread getSendingThread() { return this.sendingThread; }

    private void initializeThreads() { this.sendingThread = new Thread(getMessagesServerSender()); this.sendingThread.start(); }

    /**
     * After create instance of server start it.
     */
    public void startServer() {

        System.out.println("Server started.");

        listen();

    }

    /**
     * Endless loop for waiting new client connections. After client connected creates new thread for this connection.
     */
    private synchronized void listen() {

        while(true) {

            try {

                // Receiving hello message from any client (it means that client successfully connected to the server).
                // After that send hello message to the client from server.
                SocketController socketController = new SocketController(getServerSocket().accept());
                String incomingMessage = socketController.receive();
                String senderName = getMessageController().getSenderName(incomingMessage);

                ServerConnector clientConnection = new ServerConnector(socketController, new User(senderName), getMessages());
                clientConnection.sendHelloMessage();

                // Then create the new thread, that will listen to client messages.
                Thread clientThread = new Thread(clientConnection);
                getClientConnections().add(clientConnection);
                clientThread.start();


            }catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            try {
                Thread.sleep(10);
            }catch(InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private ServerController() {
        try {
            setServerSocket(new ServerSocket(5555));
            initializeMessages();
            setMessageController(new MessageController());
            initializeClientConnections();
            initializeMessageServerSender();
            initializeThreads();
        }catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}