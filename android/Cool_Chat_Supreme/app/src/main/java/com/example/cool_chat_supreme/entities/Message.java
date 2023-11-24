package com.example.cool_chat_supreme.entities;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;




@Entity(tableName = "messages")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private User sender;
    private User reciever;
    private String time;
    private String content;

    // Constructor
    public Message(User sender, User reciever,  String time, String content) {
        this.sender = sender;
        this.reciever = reciever;
        this.time = time;
        this.content = content;
    }

    // Getters and setters

    private static String praseTime(Date time) {
        return time.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = praseTime(time);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String dateString = dateFormat.format(this.time);
        return "(" + this.id + "," +
                this.sender.toString() + "," +
                (this.reciever != null ? this.reciever.toString() : "null") + "," +
                this.content + "," +
                this.time + ")";
    }
}