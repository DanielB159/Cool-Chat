package com.example.cool_chat_supreme.Message;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.entities.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MessageRepo {
    private MessageDao messageDao;
    private MessageRepo.MessageListData messageListData;
    private MessageAPI messageAPI;
    private int chat_id;

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();

            // when the chats are observed, fetch the data from the local DB in another thread.
            new Thread(() -> {
                messageListData.setValue(messageDao.getAllMessages());
            });
        }
    }

    // messages of a specific chat
    public MessageRepo(AppDB db, int chat_id, String baseUrl) {
        messageDao = db.messageDao();
        messageListData = new MessageRepo.MessageListData();
        this.chat_id = chat_id;
        messageAPI = new MessageAPI(messageListData, messageDao, baseUrl);
    }

    public void updateMessages(String token) {
        CompletableFuture<List<Message>> future = new CompletableFuture<>();
        new Thread(() -> {
            messageAPI.getMessages(chat_id, token, future);
            future.whenComplete(((messages, throwable) -> {
                // if the server request succeeded
                if (messages != null) {
                    // when the chats are fetched, update only the new chats added
                    messageDao.deleteAll();
                    for (Message i : messages) {
                        messageDao.insert(i);
                    }
                    messageListData.setValue(messageDao.getAllMessages());
                } else {} // if the server request failed, do nothing
            }));
        }).start();
    }

    public LiveData<List<Message>> getAll(String token) {
        messageListData.setValue(messageDao.getAllMessages());
        CompletableFuture<List<Message>> future = new CompletableFuture<>();

        new Thread(() -> {
            messageAPI.getMessages(chat_id, token, future);
            future.whenComplete(((messages, throwable) -> {
                // if the server request succeeded
                if (messages != null) {
                    // when the chats are fetched, update only the new chats added
                    messageDao.deleteAll();
                    for (Message i : messages) {
                        messageDao.insert(i);
                    }

                    messageListData.setValue(messageDao.getAllMessages());
                } else {} // if the server request failed, do nothing
            }));
        }).start();

        return messageListData;
    }

    // this function adds a new chat to the ROOM database
    public void add(final String content, String token) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        messageAPI.sendMessage(this.chat_id, content, token, future);
        future.whenComplete((things, throwable) -> {
            //update liveData
            messageListData.setValue(messageDao.getAllMessages());
            updateMessages(token);
        });
    }

    // this function deletes a chat from the ROOM database
    public void delete(final Message message) {
        messageDao.delete(message);
    }

    // this function updates a chat in the ROOM database
    public void update(final Message message) {
        messageDao.update(message);
    }


}

