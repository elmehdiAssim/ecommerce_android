package com.example.laptop.khadamat.entities;

import java.io.Serializable;

public class Message implements Serializable{

    private String idMessage;
    private String msgText;
    private String sendingDate;
    private String seen;
    private String answered;
    private User sender;
    private User Receiver;

    public String getIdMessage() {
        return idMessage;
    }

    public String getMsgText() {
        return msgText;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public String getSeen() {
        return seen;
    }

    public String getAnswered() {
        return answered;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return Receiver;
    }

    public void setIdMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        Receiver = receiver;
    }
}
