package com.example.cool_chat_supreme.ServerEntities;

import androidx.annotation.NonNull;

public class NewChatResponse {
    int id;
    UserResponse user;

    public NewChatResponse(int id, UserResponse user) {
        this.id = id;
        this.user = user;
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

    @NonNull
    @Override
    public String toString() {
        return "(id: " + this.id + ", user: " + this.user.toString() + ")";
    }
}
