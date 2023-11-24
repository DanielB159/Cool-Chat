package com.example.cool_chat_supreme.ServerEntities;

import androidx.annotation.NonNull;

public class UserResponse {
    private String username;
    private String displayName;

    private String profilePic;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public UserResponse(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    @NonNull
    @Override
    public String toString() {
        return "(username: " + this.username + ", displayName: " + this.displayName
                //+ ", profilePic: " + this.profilePic;
                + ")";
    }
}
