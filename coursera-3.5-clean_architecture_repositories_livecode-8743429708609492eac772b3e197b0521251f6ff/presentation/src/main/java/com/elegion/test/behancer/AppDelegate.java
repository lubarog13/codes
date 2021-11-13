package com.elegion.test.behancer;

import android.app.Application;

import com.elegion.test.behancer.di.AppComponent;
import com.elegion.test.behancer.di.AppModule;
import com.elegion.test.behancer.di.DaggerAppComponent;
import com.elegion.test.behancer.di.NetworkModule;

/**
 * Created by Vladislav Falzan.
 */

public class AppDelegate extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule()).build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
