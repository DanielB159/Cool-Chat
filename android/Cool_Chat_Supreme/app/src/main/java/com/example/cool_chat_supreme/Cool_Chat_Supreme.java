package com.example.cool_chat_supreme;

import android.app.Application;
import android.content.Context;

public class Cool_Chat_Supreme extends Application {
    public static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        this.context = getApplicationContext();
    }
}
