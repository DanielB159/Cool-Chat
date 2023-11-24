package com.example.cool_chat_supreme.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey
    @NonNull
    private String username;
    private String displayName;
    private int profilePic;

    public Contact(@NonNull String username, String displayName, int profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public String toString() {
        return this.getUsername() + "," + this.getDisplayName() + "," + this.getProfilePic();
    }







}
