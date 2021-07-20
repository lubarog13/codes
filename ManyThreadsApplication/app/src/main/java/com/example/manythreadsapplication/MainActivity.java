package com.example.manythreadsapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean>, Loader.OnLoadCanceledListener<Boolean> {
    ProgressBar progressBar;
    Button startButton;
    TextView textView;
    private static final int LOADER_ID = 10000;
    private LoaderManager loaderManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startButton = findViewById(R.id.button);
        this.progressBar = findViewById(R.id.progress_bar);
        this.textView = findViewById(R.id.text);
        this.loaderManager = LoaderManager.getInstance(this);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButtonStart();
            }
        });
        if (loaderManager.getLoader(LOADER_ID)!=null){
            LoaderManager.LoaderCallbacks<Boolean> loaderCallbacks = this;
            Bundle args = new Bundle();
            Loader loader = this.loaderManager.initLoader(LOADER_ID, args, loaderCallbacks);
            this.startButton.setEnabled(false);
            this.progressBar.setVisibility(View.VISIBLE);
            textView.setText("Loading...");
            loader.startLoading();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TEXT", (String) textView.getText());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString("TEXT"));
    }

    private void clickButtonStart(){
        if(loaderManager.getLoader(LOADER_ID)==null) {
            LoaderManager.LoaderCallbacks<Boolean> loaderCallbacks = this;
            Bundle args = new Bundle();
            Loader<Boolean> loader = this.loaderManager.initLoader(LOADER_ID, args, loaderCallbacks);
            loader.registerOnLoadCanceledListener(this);
            loader.forceLoad();
        }
        else {
            Log.i("info", "onLoaderFind");
            this.startButton.setEnabled(false);
            this.progressBar.setVisibility(View.VISIBLE);
            textView.setText("Loading...");
            Loader loader = this.loaderManager.getLoader(LOADER_ID);
//            loader.
//            loader.forceLoad();
        }
    }

    @NonNull
    @Override
    public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle args) {
        if(id==LOADER_ID){
            Log.i("info", "onLoaderCreate");
            this.startButton.setEnabled(false);
            this.progressBar.setVisibility(View.VISIBLE);
            textView.setText("Loading...");
            return new SomethingLoader(MainActivity.this);
        }
        throw new RuntimeException("TODO..");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean data) {
        if(loader.getId() == LOADER_ID){
            Log.i("info", "onLoaderFinish");
            this.loaderManager.destroyLoader(loader.getId());
            this.startButton.setEnabled(true);
            this.progressBar.setVisibility(View.GONE);
            textView.setText("Ready");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Boolean> loader) {
        Log.i("info", "onLoaderReset");
    }

    @Override
    public void onLoadCanceled(@NonNull Loader<Boolean> loader) {
        Log.i("info", "onLoaderCancel");
    }
}