package com.example.cool_chat_supreme.Chat;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cool_chat_supreme.ClassConverters.MessageConverter;
import com.example.cool_chat_supreme.ClassConverters.MessageListConverter;
import com.example.cool_chat_supreme.ClassConverters.UserConverter;
import com.example.cool_chat_supreme.entities.Chat;

@Database(entities = {Chat.class}, version = 1)
@TypeConverters({UserConverter.class, MessageConverter.class, MessageListConverter.class})
public abstract class ChatDatabase extends RoomDatabase {
    public abstract ChatDao chatDao();
}
