package com.example.traininglog;

import android.app.Application;

import androidx.room.Room;

import com.example.traininglog.data.Storage;
import com.example.traininglog.data.database.EsheduleDatabase;

public class AppDelegate extends Application {
    private Storage mStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        final EsheduleDatabase database = Room.databaseBuilder(this, EsheduleDatabase.class, "eshedule_database")
                                                .fallbackToDestructiveMigration()
                                                .build();
        mStorage = new Storage(database.esheduleDao());
    }

    public Storage getStorage() {
        return mStorage;
    }
}
