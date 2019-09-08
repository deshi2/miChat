package com.mirosh;

import java.util.ArrayList;

public class MessagesServerSender implements Runnable {

    private MessageController messageController;
    private ArrayList<ServerConnector> clientConnections;
    private ArrayListLock messages;

    private ArrayList<ServerConnector> getClientConnections() {
        return clientConnections;
    }

    private MessageController getMessageController() {
        return this.messageController;
    }

    private void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    private ArrayListLock getMessages() {
        return messages;
    }

    private void setMessages(ArrayListLock messages) {
        this.messages = messages;
    }

    private void setClientConnections(ArrayList<ServerConnector> clientConnections) {
        this.clientConnections = clientConnections;
    }

    public MessagesServerSender(ArrayList<ServerConnector> clientConnections, MessageController messageController, ArrayListLock messages) {
        setClientConnections(clientConnections);
        setMessageController(messageController);
        setMessages(messages);
    }

    private void sendMessages() {

        if (getMessages().getArrayList().size() !=0) {

            getMessages().lock();
            for (String sendingMessage : getMessages().getArrayList()) {
                for (ServerConnector connection : getClientConnections()) {
                    System.out.println("Sending " + getMessages().getArrayList().size() + " messages...");
                    connection.getSocketController().send(sendingMessage);
                }
            }
            getMessages().clearAll();
            getMessages().unlock();
            System.out.println("All messages are sent.");
        }
    }

    public void run() {

        while (true) {
            sendMessages();
            try {
                Thread.sleep(10);
            }catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}


