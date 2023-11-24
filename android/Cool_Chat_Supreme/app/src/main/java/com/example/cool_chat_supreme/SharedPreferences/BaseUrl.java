package com.example.cool_chat_supreme.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class BaseUrl extends AppCompatActivity {
    static BaseUrl singleton = null;
    SharedPreferences sharedPreferences;

    private BaseUrl() {
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
    }

    public static BaseUrl newBaseUrl() {
        if (singleton == null) {
            singleton = new BaseUrl();
        }
        return singleton;
    }

    public String getBaseUrl() {
        return sharedPreferences.getString("baseUrl", "http://10.0.2.2:5000/api/");
    }
}
