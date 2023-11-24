package com.example.cool_chat_supreme;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.ViewModels.ChatViewModel;
import com.example.cool_chat_supreme.ViewModels.ChatViewModelManager;
import com.example.cool_chat_supreme.entities.Chat;

import java.util.concurrent.CompletableFuture;

public class AddUserActivity extends AppCompatActivity {
    AppDB db;
    ChatViewModel chatViewModel;

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_add_user);
        chatViewModel = ChatViewModelManager.getChatViewModelWithToken();

        ImageButton imageButton = findViewById(R.id.addContactButton);
        EditText editText = findViewById(R.id.newContactIdentifier);

        // set an on click listener to add a new user
        imageButton.setOnClickListener(view -> {
            String identifier = editText.getText().toString();
            // if some identifier has been entered
            if (!identifier.equals("")) {
                CompletableFuture<Chat> future = chatViewModel.add(identifier);
                future.whenComplete(((chat, throwable) -> {
                    if (chat != null) {
//                        chatViewModel.addChatToDao(chat);
                        chatViewModel.updateChats();
                    } else {
                        Toast.makeText(AddUserActivity.this,
                                "No such user found!!!! Try again please.", Toast.LENGTH_LONG).show();
                    }
                }));
                finish();
            }
        });

    }
}