package com.example.cool_chat_supreme.Chat;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cool_chat_supreme.entities.Chat;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Chat chat);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Chat chat);

    @Delete
    void delete(Chat chat);

    @Query("SELECT * FROM chats")
    List<Chat> getAllChats();

    @Query("DELETE FROM chats")
    void deleteAllChats();
}