package com.example.cool_chat_supreme.ServerEntities;

import androidx.annotation.NonNull;

public class ChatsResponse {
    int id;
    UserResponse user;
    MessageResponse lastMessage;

    public ChatsResponse(int id, UserResponse user, MessageResponse lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public MessageResponse getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageResponse lastMessage) {
        this.lastMessage = lastMessage;
    }

    @NonNull
    @Override
    public String toString() {
        return "(id: " + this.id + ", user: " + this.user.toString()
                + ", lastMessage: " + (this.lastMessage == null ? null : this.lastMessage.toString()) + ")";
    }
}
