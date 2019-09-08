package com.mirosh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientTyper extends TCPConnector {

    private User user;
    private Viewer viewer;

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

    public void sayHelloToServer() {

        setUser(new User(getViewer().showNameDialog()));
        String helloMessage = getMessageController().createHelloMessage(getUser().getName(), "Сервер");
        getSocketController().send(helloMessage);
    }
}
