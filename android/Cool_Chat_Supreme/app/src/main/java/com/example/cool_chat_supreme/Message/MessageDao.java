package com.example.cool_chat_supreme.Message;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cool_chat_supreme.entities.Message;
import com.example.cool_chat_supreme.entities.User;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Message message);

    @Update
    void update(Message message);

    @Delete
    void delete(Message message);

    @Query("DELETE FROM Messages")
    void deleteAll();

    @Query("SELECT * FROM messages")
    List<Message> getAllMessages();

    @Query("SELECT * FROM messages WHERE id = :messageId")
    Message getMessageById(int messageId);

    @Query("SELECT * FROM messages WHERE sender = :sender")
    List<Message> getMessagesBySender(User sender);
}
