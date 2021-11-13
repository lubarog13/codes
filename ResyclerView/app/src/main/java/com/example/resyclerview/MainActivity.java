package com.example.resyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.resyclerview.items.Generator;
import com.example.resyclerview.items.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
private final RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        if(savedInstanceState==null) {
            recyclerAdapter.addData(Generator.imageItemGenerator(1));
            recyclerView.scrollToPosition(recyclerAdapter.addData(Generator.descriptionItemGenerator(1)));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_image:
                recyclerView.scrollToPosition(recyclerAdapter.addData(Generator.imageItemGenerator(1)));
                break;
            case R.id.action_add_description:
                recyclerView.scrollToPosition(
                recyclerAdapter.addData(Generator.descriptionItemGenerator(1)));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("RESYCLER", recyclerAdapter.toStringList());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String[] strings = savedInstanceState.getStringArray("RESYCLER");
        recyclerAdapter.fromStringList(Arrays.asList(strings));
    }

}