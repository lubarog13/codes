package com.example.traininglog.di;

import com.example.traininglog.data.Storage;
import com.example.traininglog.data.api.EsheduleApi;
import com.example.traininglog.ui.auth.AuthFragment;
import com.example.traininglog.ui.base.home.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
    @Component(modules = {AppModule.class, NetworkModule.class})
    public interface AppComponent {
        Storage provideStorage();
        EsheduleApi provideApiService();

        void inject(AuthFragment injector);
        void inject(HomeFragment injector);
    }
