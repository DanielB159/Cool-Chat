package com.example.cool_chat_supreme.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cool_chat_supreme.ClassConverters.MessageListConverter;

import java.util.List;

@Entity(tableName = "chats")
public class Chat {

    @PrimaryKey(autoGenerate = false)
    private int chatid;

    private User otherUser;
    private List<Message> msg;
    @Embedded
    private Message lastMessage;

    public Chat(int chatid, User otherUser, List<Message> msg, Message lastMessage) {
        this.otherUser = otherUser;
        this.msg = msg;
        this.chatid = chatid;
        this.lastMessage = lastMessage;
    }

    // Getters and setters

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }


    public User getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
    }

    public List<Message> getMsg() {
        return msg;
    }

    public void setMsg(List<Message> msg) {
        this.msg = msg;
    }

    public void setChatId(int chatId) {
        this.chatid = chatId;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public String toString() {
        return this.chatid + "," +
                MessageListConverter.listToString(this.getMsg()) + "," +
                this.otherUser.toString() + "," +
                (this.lastMessage == null ? "" : this.lastMessage.toString());
    }
}