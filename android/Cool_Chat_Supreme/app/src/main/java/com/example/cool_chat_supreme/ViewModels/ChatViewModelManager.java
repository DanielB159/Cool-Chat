package com.example.cool_chat_supreme.ViewModels;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;

public class ChatViewModelManager {

    static ChatViewModel chatViewModel;

    private ChatViewModelManager() {}

    // getting a static Singleton instance of ChatViewModel
    public static ChatViewModel getChatViewModel(AppDB db, String token, String baseUrl) {
        chatViewModel = new ChatViewModel(db, token, baseUrl);
        return chatViewModel;
    }

    public static ChatViewModel getChatViewModelWithToken() {
        return chatViewModel;
    }
}
