package com.elegion.myfirstapplication;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.elegion.myfirstapplication.db.DataBase;

public class App extends Application {

    private DataBase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mDatabase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "music_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public DataBase getDatabase() {
        return mDatabase;
    }
}
