package com.mirosh;

import javax.swing.plaf.ViewportUI;
import java.io.IOException;
import java.net.Socket;

public class ClientController {

    private Thread listenerThread; // thread for receiving messages from server
    private Thread typingThread;  // thread for typing and sending new messages to server
    private ClientTyper clientTyper;
    private ClientListener clientListener;
    private SocketController socketController;
    private Viewer viewer;

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

    private void setVier(Viewer viewer) {
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

    public ClientController() {

        try {
            setVier(new Viewer());
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

    public void startClient() {
        getViewer().showWidndow();
        clientTyper.sayHelloToServer();
        this.typingThread.start();
        this.listenerThread.start();
    }
}
