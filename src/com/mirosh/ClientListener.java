package com.mirosh;

import java.nio.charset.StandardCharsets;

public class ClientListener extends TCPConnector {

    private User user;
    private Viewer viewer;

    public ClientListener(SocketController socketController, User user, Viewer viewer) {
        super(socketController);
        setUser(user);
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

    @Override
    public void run() {
        while (true) {
            String incomingMessage = getSocketController().receive();
            getViewer().addToTextArea(getMessageController().createDisplayableText(incomingMessage));
            try {
                Thread.sleep(10);
            }catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
