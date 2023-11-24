package com.example.cool_chat_supreme.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserRepo {
    private UserDao userDao;
    private UserRepo.UserListData userListData;
    private UserAPI userAPI;

    class UserListData extends MutableLiveData<List<User>> {
        public UserListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            // This line should work on a different thread. Fix this.
//            chatListData.setValue(chatDao.getAllChats());

//            new Thread(() -> {
//                userListData.setValue(userDao.getAllUsers());
//            }).start();
        }
    }

    public UserRepo(AppDB db, String baseUrl) {
        userDao = db.userDao();
        userListData = new UserRepo.UserListData();
        userAPI = new UserAPI(userListData, userDao, baseUrl);
    }


    public LiveData<List<User>> getAll() {
        userListData.setValue(userDao.getAllUsers());
        return userListData;
    }

    // this function adds a new chat to the ROOM database
    public void add(final User user,CompletableFuture<String> future ) {
        userAPI.addUser(user,future);
    }


    // this function gets the details to the logged in user
    public CompletableFuture<User> getUser(String username, String token, String firebaseToken) {
        CompletableFuture<User> future = new CompletableFuture<>();
        // set the thread to get the user details from the service
        new Thread(() -> {
            userAPI.getUser(username, token, future, firebaseToken);
        }).start();
        return future;
    }

    // this function deletes a chat from the ROOM database
    public void delete(final User user) {
        userDao.delete(user);
    }

    // this function updates a chat in the ROOM database
    public void update(final User user) {
        userDao.update(user);
    }

    public CompletableFuture<String> getToken(User user) {
        CompletableFuture<String> future = new CompletableFuture<>();
        // setting the request from the server to happen in a different thread
        new Thread(() ->{
            userAPI.getToken(user, future);
        }).start();

        return future;
    }

}

