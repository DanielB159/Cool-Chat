package com.example.cool_chat_supreme.ViewModels;

import androidx.lifecycle.LiveData;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.Message.MessageRepo;
import com.example.cool_chat_supreme.entities.Message;

import java.util.List;

public class MessageViewModel {
    public MessageRepo messageRepository;
    public LiveData<List<Message>> messages;
    public String token;
    public int chat_id;

    public MessageViewModel(AppDB db, int chat_id, String token, String baseUrl) {
        this.chat_id = chat_id;
        messageRepository = new MessageRepo(db, chat_id, baseUrl);
        this.token = token;
        messages = messageRepository.getAll(token);
    }

    // get function for the messages (by id)
    public LiveData<List<Message>> getMessages() {
        return messageRepository.getAll(token);
    }

    public void updateMessages() {
        messageRepository.updateMessages(token);
    }

    public void add(String content) {
        messageRepository.add(content, this.token);
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }

    public void update(Message message) {
        messageRepository.update(message);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }
}