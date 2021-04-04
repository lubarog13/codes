package com.example.recicle;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class AndroidVersion extends AppCompatActivity {
    private TextView name;
    private ImageView image;
    private ImageView background;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_view);
        name = findViewById(R.id.name);
        image = findViewById(R.id.icon);
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        Picasso.get().load(intent.getStringExtra("image")).resize(300, 300).centerCrop().into(image);
        background = findViewById(R.id.background);
        Picasso.get().load("https://i.pinimg.com/564x/e7/c6/df/e7c6df386e4d410169d5f47c19a1bdc6.jpg").resize(1000,400).into(background);
    }
}
