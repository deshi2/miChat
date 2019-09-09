package com.mirosh;

/**
 * Contains {@link com.mirosh.SocketController} and {@link com.mirosh.MessageController} objects for simplify future
 * use (inherit this class). Also contains run() method to override in child classes.
 */
abstract public class TCPConnector implements Runnable {

    private SocketController socketController;
    private MessageController messageController;

    {
        setMessageController(new MessageController());
    }

    public TCPConnector(SocketController socketController) {
        setSocketController(socketController);
    }

    @Override
    public void run() {
    }

    /* SETTERS AND GETTERS */

    private void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    protected MessageController getMessageController() {
        return this.messageController;
    }

    private void setSocketController(SocketController socketController) {
        this.socketController = socketController;
    }

    protected SocketController getSocketController() {
        return this.socketController;
    }

}