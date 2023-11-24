package com.example.cool_chat_supreme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.DatabaseROOM.AppDatabase;
import com.example.cool_chat_supreme.ViewModels.ChatViewModel;
import com.example.cool_chat_supreme.ViewModels.ChatViewModelManager;
import com.example.cool_chat_supreme.ViewModels.UserViewModel;
import com.example.cool_chat_supreme.adapters.ContactListAdapter;
import com.example.cool_chat_supreme.adapters.SelectListener;
import com.example.cool_chat_supreme.entities.Chat;
import com.example.cool_chat_supreme.entities.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.CompletableFuture;


public class ChatContactsActivity extends AppCompatActivity implements SelectListener {
    private AppDB db;
    ChatViewModel chatViewModel;
    UserViewModel userViewModel;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_chat_contacts);

        ImageView settings=findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatContactsActivity.this, SettingsPage.class);
                startActivity(intent);
                finish();
            }
        });

        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        final ContactListAdapter adapter = new ContactListAdapter(this, this);
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));

        String username = "", token = "", firebaseToken = "";
        db = AppDatabase.getDB();
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("username");
            token = getIntent().getExtras().getString("token");
            firebaseToken = getIntent().getExtras().getString("firebaseToken");
        } else {
            finish();
        }

        // creating an instance of view Model.
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        String baseUrl = sharedPreferences.getString("baseUrl", "http://10.0.2.2:5000/api/");
        chatViewModel = ChatViewModelManager.getChatViewModel(db, token, baseUrl);
        userViewModel = new UserViewModel(db, baseUrl);

        // requesting the server for user details and sending firebase token
        CompletableFuture<User> future = userViewModel.getUser(username, token, firebaseToken);
        future.whenComplete(((user, throwable) -> {
            if (user != null) {
                ImageView imageView = findViewById(R.id.userPicture);
                TextView textView = findViewById(R.id.userIdentifier);
                String base64Image = user.getProfilePic();
                byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageView.setImageBitmap(bitmap);
                textView.setText(user.getDisplayName());
            } else {
            } // if the request for the details failed, keep default
        }));

        chatViewModel.deleteAllChats();
        chatViewModel.updateChats();
        // creating a list of observable chats and inserting them to the view
        chatViewModel.getChats().observe(this, chats -> {
            adapter.setChats(chats);
        });


        FloatingActionButton addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener((view) -> {
            Intent intent = new Intent(ChatContactsActivity.this, AddUserActivity.class);
            startActivity(intent);
        });

        ImageView logout = findViewById(R.id.logout);
        SharedPreferences sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
        String sharedToken = sharedPreferences.getString("token", "");
        String sharedUsername = sharedPreferences.getString("username", "");
        String sharedFirebaseToken = sharedPreferences.getString("firebaseToken" , "");

        logout.setOnClickListener((view) -> {
            editor = sharedPreferences.edit();
            //reset sharedPreferences
            editor.putString("token", "");
            editor.putString("username", "");
            editor.putString("firebaseToken", "");
            editor.commit();

            // send to the server that user has logged out
            finish();
        });
    }

    @Override
    public void onItemClicked(Chat chat) {
        // put in intent the details of the current user and their token
        Intent intent = new Intent(ChatContactsActivity.this, ChatActivity.class);
        intent.putExtra("token", getIntent().getExtras().getString("token"));
        intent.putExtra("username", getIntent().getExtras().getString("username"));
        intent.putExtra("chat_id", chat.getChatid());
        intent.putExtra("contact_display_name", chat.getOtherUser().getDisplayName());
        intent.putExtra("contact_pfofile_pic", chat.getOtherUser().getProfilePic());
        startActivity(intent);
    }

    @Override
    public void onItemLongClicked(Chat chat) {
        chatViewModel.delete(chat);
    }

}