package com.mirosh;

import java.util.ArrayList;

/**
 * Can send messages from array of messages to all known clients (client connections contains in another arrayList).
 */
public class MessagesServerSender implements Runnable {

    private MessageController messageController;
    private ArrayList<ServerConnector> clientConnections; // all client connections array
    private ArrayListLock messages;                       // messages foe send queue

    /**
     *
     * @param clientConnections Uses existing {@link com.mirosh.ServerConnector} ArrayList instance for sending messages
     *                          to clients (client connections) from this array list.
     * @param messageController use existing or new {@link com.mirosh.MessageController} instance for pack/unpack
     *                          messages using JSON format.
     * @param messages the queue to be send to clients.
     */
    public MessagesServerSender(ArrayList<ServerConnector> clientConnections, MessageController messageController, ArrayListLock messages) {
        setClientConnections(clientConnections);
        setMessageController(messageController);
        setMessages(messages);
    }

    /**
     * Send messages from the queue to all connected clients. When queue of messages becomes not empty, then
     * lock it, send messages, delete it and then, finally,  unlock.
     */
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

    /**
     * Endless loop of sending messages from the queue.
     */
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

    /* SETTERS AND GETTERS */

    private void setClientConnections(ArrayList<ServerConnector> clientConnections) {
        this.clientConnections = clientConnections;
    }

    private ArrayList<ServerConnector> getClientConnections() {
        return clientConnections;
    }

    private MessageController getMessageController() {
        return this.messageController;
    }

    private void setMessageController(MessageController messageController) {
        this.messageController = messageController;
    }

    private void setMessages(ArrayListLock messages) {
        this.messages = messages;
    }

    private ArrayListLock getMessages() {
        return messages;
    }

}