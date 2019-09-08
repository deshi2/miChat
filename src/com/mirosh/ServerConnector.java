package com.mirosh;

/**
 * Connection og client (seeing from the server side, that's why named ServerConnector). i.e. class who rules
 * client connections.
 */
public class ServerConnector extends TCPConnector {

    private User user;

    private ArrayListLock messages;

    /**
     *
     * @param socketController {@link com.mirosh.SocketController} Object, controls the socket and can directly send messages and receive to/from server.
     * @param user {@link com.mirosh.User} user instance, contains user attributes, where "name" is the main.
     * @param messages array of messages, i.e. message queue.
     * */
    public ServerConnector(SocketController socketController, User user, ArrayListLock messages) {
        super(socketController);
        setUser(user);
        setMessages(messages);
    }

    private ArrayListLock getMessages() {
        return this.messages;
    }

    private void setMessages(ArrayListLock messages) {
        this.messages = messages;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    /**
     * Send to the server special kind of message that server see like service message means clientName successfully
     * connected to server".
     */
    public void sendHelloMessage() {
        getSocketController().send(getMessageController().createHelloMessage("Сервер", getUser().getName()));
    }

    /**
     * Endless loop for receiving messages from client and put them into the message queue.
     */
    @Override
    public void run() {

        while(true) {
            String incomingMessage = getSocketController().receive();
            getMessages().lock();
            System.out.println(incomingMessage);
            getMessages().addElement(incomingMessage);
            getMessages().unlock();
            try {
                Thread.sleep(10);
            }catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}