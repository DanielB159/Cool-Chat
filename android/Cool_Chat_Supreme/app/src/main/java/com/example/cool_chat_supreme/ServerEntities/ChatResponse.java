package com.example.cool_chat_supreme.ServerEntities;

import androidx.annotation.NonNull;

import java.util.List;

public class ChatResponse {
    int id;
    List<UserResponse> users;
    List<MessageResponse> messages;

    public ChatResponse(int id, List<UserResponse> users, List<MessageResponse> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<UserResponse> getUser() {
        return users;
    }

    public void setUser(List<UserResponse> users) {
        this.users = users;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public String toString() {
        return "(id: " + this.id + ", user: " + this.users.toString()
                + ", lastMessage: " + this.messages.toString() + ")";
    }
}
