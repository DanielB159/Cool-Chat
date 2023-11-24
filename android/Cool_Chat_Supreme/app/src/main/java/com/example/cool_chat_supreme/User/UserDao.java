package com.example.cool_chat_supreme.User;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cool_chat_supreme.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE username = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM user WHERE username = :username")
    User getUserByUsername(String username);

}

