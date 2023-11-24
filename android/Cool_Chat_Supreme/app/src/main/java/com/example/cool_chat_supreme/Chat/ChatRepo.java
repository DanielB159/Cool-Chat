package com.example.cool_chat_supreme.Chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.entities.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChatRepo {
    private ChatDao chatDao;
    private ChatListData chatListData;
    private ChatAPI chatAPI;

    class ChatListData extends MutableLiveData<List<Chat>> {
        public ChatListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            // when the chats are observed, fetch the data from the local DB in another thread.
            new Thread(() -> {
                chatListData.setValue(chatDao.getAllChats());
            });
        }
    }

    public ChatRepo(AppDB db, String baseUrl) {
        chatDao = db.chatDao();
        chatListData = new ChatListData();
        chatAPI = new ChatAPI(chatListData, chatDao, baseUrl);
    }

    public void insertChat(Chat chat) {
        chatDao.insert(chat);
        chatListData.setValue(chatDao.getAllChats());
    }

    public void updateChats(String token) {
        CompletableFuture<List<Chat>> future = new CompletableFuture<>();
        new Thread(() -> {
            chatAPI.getChats(token, future);
            future.whenComplete(((chats, throwable) -> {
                // if the server request succeeded
                if (chats != null) {
                    chatDao.deleteAllChats();
                    for (Chat i : chats) {
                        chatDao.insert(i);
                    }
                    chatListData.setValue(chatDao.getAllChats());
                } else {
                } // if the server request failed, do nothing
            }));
        }).start();
    }

    public LiveData<List<Chat>> getAll(String token) {
        // first, update the live data according to the local DB
        chatListData.setValue(chatDao.getAllChats());
        // next, fetch the live chats from the server
        CompletableFuture<List<Chat>> future = new CompletableFuture<>();
        new Thread(() -> {
            chatAPI.getChats(token, future);
            future.whenComplete(((chats, throwable) -> {
                // if the server request succeeded
                if (chats != null) {
                    chatDao.deleteAllChats();
                    for (Chat i : chats) {
                        chatDao.insert(i);
                    }
                    chatListData.setValue(chatDao.getAllChats());
                } else {
                } // if the server request failed, do nothing
            }));
        }).start();
        return chatListData;
    }

    public void deletelAllChats() {
        chatDao.deleteAllChats();
    }

    // this function adds a new chat to the ROOM database
    public CompletableFuture<Chat> add(String username, String token) {
        CompletableFuture<Chat> future = new CompletableFuture<>();
        chatAPI.createChat(username, token, future);
        return future;
    }

//    public void addNewChat

    // this function deletes a chat from the ROOM database
    public void delete(final Chat chat, String token) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        // set the new thread to request the server to delete the chat
        new Thread(() -> {
            chatAPI.deleteChat(chat.getChatid(), token, future);
        }).start();
        future.whenComplete(((response, throwable) -> {
            if (response) {
                chatDao.delete(chat);
                chatListData.setValue(chatDao.getAllChats());
            } else { } // if the deletion failed, do nothing
        }));
    }

    // this function updates a chat in the ROOM database
    public void update(final Chat chat) {
        chatDao.update(chat);
    }
}
