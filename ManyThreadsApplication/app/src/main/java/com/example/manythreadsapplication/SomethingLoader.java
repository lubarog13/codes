package com.example.manythreadsapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.concurrent.TimeUnit;

public class SomethingLoader extends AsyncTaskLoader <Boolean> {


    public SomethingLoader(@NonNull Context context) {
        super(context);

    }


    @Nullable
    @Override
    public Boolean loadInBackground() {
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
