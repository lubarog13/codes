package com.example.simpleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText text;
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private String startText = "Ничего не введено";
    private Button button;
    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(text.getText().length()>0) {
                Toast.makeText(MainActivity.this, text.getText().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, text.getText().toString());
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(mButtonListener);
    }
}