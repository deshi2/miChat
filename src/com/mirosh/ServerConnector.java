package com.mirosh;

public class ServerConnector extends TCPConnector {

    User user;

    private ArrayListLock messages;

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

    public void sendHelloMessage() {
        getSocketController().send(getMessageController().createHelloMessage("Сервер", getUser().getName()));
    }

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
