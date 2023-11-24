package com.example.cool_chat_supreme.ServerEntities;

import androidx.annotation.NonNull;

public class MessageResponse {
    int id;
    String created;
    UserResponse sender;    //will only have username in it
    UserResponse receiver;
    String content;

    public MessageResponse(int id, String created, UserResponse sender, UserResponse receiver, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public UserResponse getSender() {
        return sender;
    }

    public void setSender(UserResponse sender) {
        this.sender = sender;
    }

    public UserResponse getReceiver() {
        return receiver;
    }

    public void setReceiver(UserResponse receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return "(id: " + this.id + ", created: " + this.created + ", sender: " + sender.toString()
                + ", content: " + content + ")";
    }
}
