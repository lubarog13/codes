package com.elegion.test.behancer.di;

import android.arch.persistence.room.Room;

import com.elegion.data.database.BehanceDao;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.data.Storage;
import com.elegion.data.database.BehanceDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanchuev on 23.04.2018.
 */

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
    BehanceDatabase provideDatabase() {
        return Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    BehanceDao provideBehanceDao(BehanceDatabase database) {
        return database.getBehanceDao();
    }

    @Provides
    @Singleton
    Storage provideStorage(BehanceDao dao) {
        return new Storage(dao);
    }
}
