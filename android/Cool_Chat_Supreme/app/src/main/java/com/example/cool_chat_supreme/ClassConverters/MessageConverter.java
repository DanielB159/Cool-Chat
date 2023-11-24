package com.example.cool_chat_supreme.ClassConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.cool_chat_supreme.entities.Message;
import com.example.cool_chat_supreme.entities.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MessageConverter {
    @TypeConverter
    public static Message fromString(String value) {
        if (value == null) {
            return null;
        }
        // Parse the value and create a new Message object
        String[] parts = value.split(",");
        if (parts.length != 8) {
            return null;
        }
        String senderUserString = parts[1] + parts[2] + parts[3];
        User sender = UserConverter.fromString(senderUserString);
        String receiverUserString = parts[4] + parts[5] + parts[6];
        User receiver = UserConverter.fromString(receiverUserString);
        Message msg = new Message(sender, receiver, parts[8], parts[7]);
        return msg;
    }


    @TypeConverter
    public static String toString(Message msg) {
        // Convert the User object to a string representation
        if (msg != null) {
            return msg.toString();
        } else {
            return null;
        }
    }
}
