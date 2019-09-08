package com.mirosh;

import java.io.IOException;
import java.net.Socket;

/**
 * Rules all main operations on client, i.e. creates new threads, shows windows to user etc.
 * The idea is to have 2 main threads on the clint application: one for listening for new messages from server (and
 * show new message after receiving) and another for sending messages after user commanded to send.
 */
public class ClientController {

    private Thread listenerThread;            // thread for receiving messages from server
    private Thread typingThread;              // thread for typing and sending new messages to server
    private ClientTyper clientTyper;          // contains thread for type and send new messages to the server
    private ClientListener clientListener;    // contains thread for receiving messages from server and showing them
    private SocketController socketController;// controls connection, can send and receive messages via tcp/ip sockets
    private Viewer viewer;                    // controls visual side, show windows, rules window elements etc

    private void setListenerThread(Thread thread) {
        this.listenerThread = thread;
    }

    private Thread getListenerThread() {
        return this.listenerThread;
    }

    private void setTypingThread(Thread thread) {
        this.typingThread = thread;
    }

    private Thread getTypingThread() {
        return this.typingThread;
    }

    private void setClientTyper(ClientTyper clientTyper) {
        this.clientTyper = clientTyper;
    }

    private ClientTyper getClientTyper() {
        return this.clientTyper;
    }

    private void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }

    private ClientListener getClientListener() {
        return this.clientListener;
    }

    private void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    private Viewer getViewer() {
        return this.viewer;
    }

    private void setSocketController(SocketController socketController) {
        this.socketController = socketController;
    }

    private SocketController getSocketController() {
        return this.socketController;
    }

    /**
     * Connecting to server after been opened and shown main window.
     */
    public ClientController() {

        try {
            setViewer(new Viewer());
            Socket socket = new Socket("127.0.0.1", 5555);
            setSocketController(new SocketController(socket));

            setClientTyper(new ClientTyper(getSocketController(), getViewer()));
            setTypingThread(new Thread(getClientTyper()));

            setClientListener(new ClientListener(getSocketController(), getClientTyper().getUser(), getViewer()));
            setListenerThread(new Thread(getClientListener()));

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Use to open client application and start to work (start all client threads).
     */
    public void startClient() {
        getViewer().showWidndow();
        clientTyper.sayHelloToServer();
        this.typingThread.start();
        this.listenerThread.start();
    }
}