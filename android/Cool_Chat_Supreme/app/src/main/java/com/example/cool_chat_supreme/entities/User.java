package com.example.cool_chat_supreme.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String username;

    private String displayName;
    private String profilePic;

    private String password;

    private String token;

    // Constructor, getters, and setters
    public User(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return this.getUsername() + "," +
                this.getPassword() + "," +
                this.getDisplayName() + "," +
                this.getProfilePic();
    }
}

