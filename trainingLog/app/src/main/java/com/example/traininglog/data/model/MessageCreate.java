package com.example.traininglog.data.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageCreate{
    @SerializedName("heding")
    private String heding;
    @SerializedName("message")
    private String message;
    @SerializedName("send_time")
    private String send_time;
    @SerializedName("sender")
    private int sender;
    @SerializedName("recipient")
    private  int recipient;

    public MessageCreate(String heding, String message, Date send_time, int sender, int recipient) {
        this.heding = heding;
        this.message = message;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        this.send_time = format.format(send_time).replace(" ", "T");
        this.sender = sender;
        this.recipient = recipient;
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            return format.parse(send_time.replace("T", " "));
        } catch (ParseException e) {
            return null;
        }
    }

    public void setSend_time(Date send_time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        this.send_time = format.format(send_time).replace(" ", "T");
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }
}
