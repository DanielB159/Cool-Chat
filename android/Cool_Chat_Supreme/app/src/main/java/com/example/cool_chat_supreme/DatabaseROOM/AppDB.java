package com.example.cool_chat_supreme.DatabaseROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cool_chat_supreme.Chat.ChatDao;
//import com.example.cool_chat_supreme.ClassConverters.ChatConverter;
import com.example.cool_chat_supreme.ClassConverters.MessageConverter;
import com.example.cool_chat_supreme.ClassConverters.MessageListConverter;
import com.example.cool_chat_supreme.ClassConverters.UserConverter;
import com.example.cool_chat_supreme.Message.MessageDao;
import com.example.cool_chat_supreme.User.UserDao;
import com.example.cool_chat_supreme.entities.Chat;
import com.example.cool_chat_supreme.entities.Contact;
import com.example.cool_chat_supreme.entities.Message;
import com.example.cool_chat_supreme.entities.User;

@Database(entities = {Chat.class, User.class, Contact.class, Message.class}, version = 8)
@TypeConverters({UserConverter.class, MessageConverter.class, MessageListConverter.class})
public abstract class AppDB extends RoomDatabase {
    public abstract ChatDao chatDao();
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
}
