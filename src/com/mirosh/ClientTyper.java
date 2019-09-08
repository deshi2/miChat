package com.mirosh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Allows user type new chat messages and send it to the server. Extends {@link com.mirosh.TCPConnector}.
 */
public class ClientTyper extends TCPConnector {

    private User user;
    private Viewer viewer;

    /**
     * Initialize fields.
     * @param socketController {@link com.mirosh.SocketController} controls socket, send/receive messages directly
     * @param viewer {@link com.mirosh.Viewer} rules elements on the window
     */
    public ClientTyper(SocketController socketController, Viewer viewer) {
        super(socketController);
        setViewer(viewer);
    }

    private void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    private Viewer getViewer() {
        return this.viewer;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public String readFromConsole() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return bufferedReader.readLine();
        }catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Endless loop where if user entered new message and put send button, this messages will be sent.
     */
    @Override
    public void run() {
        while (true) {

            String text = getViewer().getMessage();
            System.out.println(text);
            if (text != "")
                getSocketController().send(getMessageController().createMessage(getUser().getName(), text));
            getViewer().setMessage("");
            try {
                Thread.sleep(1);
            }catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sends to the server a special kind of message that means "hello, server, my name is user.getName()".
     * Server will process this messages in a special way.
     */
    public void sayHelloToServer() {

        setUser(new User(getViewer().showNameDialog()));
        String helloMessage = getMessageController().createHelloMessage(getUser().getName(), "Сервер");
        getSocketController().send(helloMessage);
    }
}