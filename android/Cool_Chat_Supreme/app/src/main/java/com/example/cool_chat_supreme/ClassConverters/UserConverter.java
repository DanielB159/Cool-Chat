package com.example.cool_chat_supreme.ClassConverters;

import androidx.room.TypeConverter;

import com.example.cool_chat_supreme.entities.User;

public class UserConverter {
    @TypeConverter
    public static User fromString(String value) {
        if (value.equals("null")) {
            return null;
        }
        // Parse the value and create a new User object
        String[] parts = value.split(",");
        User user = new User(parts[0], parts[1], parts[2], parts[3]);
        return user;
    }

    @TypeConverter
    public static String toString(User user) {
        // Convert the User object to a string representation
        return user != null ? user.toString() : "null";
    }
}
