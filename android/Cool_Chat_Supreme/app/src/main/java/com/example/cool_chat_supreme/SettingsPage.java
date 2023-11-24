package com.example.cool_chat_supreme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cool_chat_supreme.DatabaseROOM.AppDatabase;
import com.example.cool_chat_supreme.ViewModels.UserViewModel;
import com.example.cool_chat_supreme.entities.User;

public class SettingsPage extends AppCompatActivity {
    boolean nightMode = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView modeStatus;

    UserViewModel userViewModel;


    String baseUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        modeStatus = findViewById(R.id.mode);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);
        baseUrl = sharedPreferences.getString("baseUrl", "http://10.0.2.2:5000/api/");

        Button daybutton = findViewById(R.id.setNight);

        daybutton.setOnClickListener((view) -> {
            if (!nightMode) {
                editor = sharedPreferences.edit();
                editor.putBoolean("night", true);
                editor.commit();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                nightMode = sharedPreferences.getBoolean("night", false);

            }
        });

        Button nightButton = findViewById(R.id.setDay);

        nightButton.setOnClickListener((view) -> {
            if (nightMode) {
                editor = sharedPreferences.edit();
                editor.putBoolean("night", false);
                editor.commit();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                nightMode = sharedPreferences.getBoolean("night", false);
            }
        });

        EditText inputServerPort = findViewById(R.id.inputServer);
        inputServerPort.setHint(baseUrl);

        @SuppressLint("UseButton")
        Button changeServer = findViewById(R.id.changeServer);
        changeServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User dummyUser = new User("", "", "", "");
                String url = inputServerPort.getText().toString();
                try {
                    userViewModel = new UserViewModel(AppDatabase.getDB(), url);
                    editor = sharedPreferences.edit();
                    editor.putString("baseUrl", inputServerPort.getText().toString());
                    editor.commit();
                    baseUrl = sharedPreferences.getString("baseUrl", "http://10.0.2.2:5000/api/");
                    inputServerPort.setHint(baseUrl);
                    inputServerPort.setText("");
                } catch (IllegalArgumentException e) {
                    Toast.makeText(SettingsPage.this,
                            e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
