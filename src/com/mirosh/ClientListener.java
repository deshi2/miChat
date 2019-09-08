package com.mirosh;

/**
 * Can listen messages from server and show it with the help of {@link com.mirosh.Viewer}.
 */
public class ClientListener extends TCPConnector {

    private User user;
    private Viewer viewer;

    /**
     * Initialize fields with existing objects.
     * @param socketController {@link com.mirosh.SocketController} rules socket connection, can sand or receive messages
     *                                                            directly
     * @param user {@link com.mirosh.User} describes existing user, the main field is name
     * @param viewer {@link com.mirosh.Viewer} shows control elements on the screen
     */
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

    /**
     * Endless loop for receiving messages and showing them on the screen.
     */
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