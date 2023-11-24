package com.example.cool_chat_supreme.ViewModels;

import androidx.lifecycle.LiveData;

import com.example.cool_chat_supreme.Chat.ChatRepo;
import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.entities.Chat;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChatViewModel {
    public ChatRepo chatRepository;
    public LiveData<List<Chat>> chats;
    String token;

    public ChatViewModel(AppDB db, String token, String baseUrl) {
        chatRepository = new ChatRepo(db, baseUrl);
        this.token = token;
        chats = chatRepository.getAll(token);
    }

    // get function for the live chats
    public LiveData<List<Chat>> getChats() {
        return chatRepository.getAll(this.token);
    }

    public CompletableFuture<Chat> add(String username) {
        return chatRepository.add(username, token);
    }
    public void updateChats() {
        chatRepository.updateChats(token);
    }

    public void addChatToDao(Chat chat) {
        chatRepository.insertChat(chat);
    }

    public void deleteAllChats() {
        chatRepository.deletelAllChats();
    }

    public void delete(Chat chat) {
        chatRepository.delete(chat, token);
    }

    public void update(Chat chat) {
        chatRepository.update(chat);
    }
}
