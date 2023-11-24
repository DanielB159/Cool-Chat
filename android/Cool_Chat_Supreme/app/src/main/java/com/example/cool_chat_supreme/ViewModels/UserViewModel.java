package com.example.cool_chat_supreme.ViewModels;

import androidx.lifecycle.LiveData;

import com.example.cool_chat_supreme.User.UserRepo;
import com.example.cool_chat_supreme.entities.User;


import com.example.cool_chat_supreme.DatabaseROOM.AppDB;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserViewModel {
    public UserRepo userRepositiory;
    public LiveData<List<User>> users;

    public UserViewModel(AppDB db, String baseUrl) {
        userRepositiory = new UserRepo(db, baseUrl);
        users = userRepositiory.getAll();
    }

    // get function for the live users
    public LiveData<List<User>> getUsers() {
        return userRepositiory.getAll();
    }

    public CompletableFuture<User> getUser(String username, String token, String firebaseToken) {
        return userRepositiory.getUser(username, token, firebaseToken);
    }


    public void add(User user, CompletableFuture<String> future) {
        userRepositiory.add(user,future);
    }

    public void delete(User user) {
        userRepositiory.delete(user);
    }

    public void update(User user) {
        userRepositiory.update(user);
    }

    public CompletableFuture<String> getToken(User user) {return userRepositiory.getToken(user);}
}

