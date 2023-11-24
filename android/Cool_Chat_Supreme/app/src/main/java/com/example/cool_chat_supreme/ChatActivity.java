package com.example.cool_chat_supreme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cool_chat_supreme.DatabaseROOM.AppDB;
import com.example.cool_chat_supreme.DatabaseROOM.AppDatabase;
import com.example.cool_chat_supreme.ViewModels.MessageViewModel;
import com.example.cool_chat_supreme.ViewModels.MessageViewModelManager;
import com.example.cool_chat_supreme.ViewModels.UserViewModel;
import com.example.cool_chat_supreme.adapters.MessageListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private AppDB db;
    MessageViewModel messageViewModel;
    UserViewModel userViewModel;

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_chat);

        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        final MessageListAdapter adapter = new MessageListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));


        String username = "", token = "", displayName = "", profilePic = "";
        int chat_id = -1;

        db = AppDatabase.getDB();
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("username");
            token = getIntent().getExtras().getString("token");
            chat_id = getIntent().getExtras().getInt("chat_id");
            displayName = getIntent().getExtras().getString("contact_display_name");
            profilePic = getIntent().getExtras().getString("contact_pfofile_pic");
        } else {
            finish();
        }

        // first, set the details of the contact
        ImageView contactImage = findViewById(R.id.contactImageChat);
        TextView contactDisplayName = findViewById(R.id.contactDisplayNameChat);

        byte[] imageBytes = Base64.decode(profilePic, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        contactImage.setImageBitmap(bitmap);
        contactDisplayName.setText(displayName);

        // set up the details of the chat in the chat adapter
        adapter.setUsername(username);
        // creating an instance of view Model.
        SharedPreferences sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        String baseUrl = sharedPreferences.getString("baseUrl", "http://10.0.2.2:5000/api/");
        messageViewModel = MessageViewModelManager.getMessageViewModel(db, chat_id, token, baseUrl);
        //userViewModel = new UserViewModel(db);

        messageViewModel.getMessages().observe(this, messages -> {
            adapter.setMessages(messages);
            //scroll all the way down
            lstMessages.scrollToPosition(adapter.getItemCount() - 1);
        });

        final String finalUsername = username;
        ImageButton sendButton = findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(view -> {
            EditText editText = findViewById(R.id.sendMessage);

            Date currentTime = new Date();
            // Define the desired date format
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
            // Format the current time as a string
            String currentTimeString = sdf.format(currentTime);

            if (editText.getText().toString().equals("")) {
                return;
            }
            messageViewModel.add(editText.getText().toString());
            editText.setText("");
        });
        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, ChatContactsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}