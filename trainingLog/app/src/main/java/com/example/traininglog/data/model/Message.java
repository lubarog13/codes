package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {
    @SerializedName("id")
    private int id;
    @SerializedName("heding")
    private String heding;
    @SerializedName("message")
    private String message;
    @SerializedName("send_time")
    private Date send_time;
    @SerializedName("sender")
    private User sender;
    @SerializedName("recipient")
    private  User recipient;

    public Message(int id) {
        this.id = id;
    }

    public Message(int id, String heding, String message, Date send_time, User sender, User recipient) {
        this.id = id;
        this.heding = heding;
        this.message = message;
        this.send_time = send_time;
        this.sender = sender;
        this.recipient = recipient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeding() {
        return heding;
    }

    public void setHeding(String heding) {
        this.heding = heding;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}
