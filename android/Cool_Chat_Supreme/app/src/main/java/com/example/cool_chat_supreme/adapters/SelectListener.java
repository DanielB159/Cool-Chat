package com.example.cool_chat_supreme.adapters;

import com.example.cool_chat_supreme.entities.Chat;

public interface SelectListener {
    void onItemClicked(Chat chat);

    void onItemLongClicked(Chat chat);
}
