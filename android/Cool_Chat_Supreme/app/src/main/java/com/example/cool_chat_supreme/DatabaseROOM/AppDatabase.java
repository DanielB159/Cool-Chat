package com.example.cool_chat_supreme.DatabaseROOM;

import androidx.room.Room;

import com.example.cool_chat_supreme.Cool_Chat_Supreme;


// This class is a singleton class for the application database
public class AppDatabase {
    private static AppDB db = null;

    private AppDatabase() {}

    public static AppDB getDB() {
        if (db == null) {
            db = Room.databaseBuilder(Cool_Chat_Supreme.context,
                            AppDB.class, "RoomDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}
