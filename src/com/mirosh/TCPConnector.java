package com.mirosh;

abstract public class TCPConnector implements Runnable {

    private SocketController socketController;
    private MessageController messageController;

    public TCPConnector(SocketController socketController) {
        setSocketController(socketController);
        setMessageController(new MessageController());
    }

    private void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    private void setSocketController(SocketController socketController) {
        this.socketController = socketController;
    }

    protected SocketController getSocketController() {
        return this.socketController;
    }

    protected MessageController getMessageController() {
        return this.messageController;
    }

    @Override
    public void run() {
    }

}
