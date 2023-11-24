package com.example.cool_chat_supreme.ViewModels;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;

public class MessageViewModelManager {
    static MessageViewModel messageViewModel;

    private MessageViewModelManager() {}

    // getting a static Singleton instance of ChatViewModel
    public static MessageViewModel getMessageViewModel(AppDB db, int chat_id , String token, String baseUrl) {
        if (messageViewModel == null) {
            messageViewModel = new MessageViewModel(db, chat_id,token, baseUrl);
        } else {
            messageViewModel = new MessageViewModel(db, chat_id, token, baseUrl);
        }
        return messageViewModel;
    }

    public static MessageViewModel getMessageViewModelWithToken() {
        return messageViewModel;
    }
}