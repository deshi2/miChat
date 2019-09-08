package com.mirosh;

public class User {

    private String name;
    private Condition condition;

    public User(String name) {
        this.setName(name);
        this.setCondition(Condition.IN_CHATROOM);
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Condition getCondition() {
        return this.condition;
    }

    private static enum Condition {
        IN_GAME,
        IN_CHATROOM;

        private Condition() {
        }
    }
}