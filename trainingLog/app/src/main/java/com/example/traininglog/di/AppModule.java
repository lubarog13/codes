package com.example.traininglog.di;

import androidx.room.Room;

import com.example.traininglog.AppDelegate;
import com.example.traininglog.data.Storage;
import com.example.traininglog.data.database.EsheduleDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final AppDelegate mApp;

    public AppModule(AppDelegate mApp) {
        this.mApp = mApp;
    }

    @Provides
    @Singleton
    AppDelegate provideApp() {
        return mApp;
    }

    @Provides
    @Singleton
    Storage provideStorage() {
        final EsheduleDatabase database = Room.databaseBuilder(mApp, EsheduleDatabase.class, "eshedule_database")
                .fallbackToDestructiveMigration()
                .build();

        return new Storage(database.esheduleDao());
    }
}
