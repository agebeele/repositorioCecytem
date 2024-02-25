package com.example.dual.chatBotVIN;

public class ChatBot_VINMessage {
    private String sender;
    private String message;

    public ChatBot_VINMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
