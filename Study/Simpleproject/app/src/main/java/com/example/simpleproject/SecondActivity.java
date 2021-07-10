package com.example.simpleproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private TextView text;
    private String startText = "Ничего не введено";
    private String txt;
    private Button button;
    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String newTxt = txt.replace(' ', '+');
            String google = "https://www.google.com/search?q="+newTxt+"&rlz=1C1GIVA_enRU947RU947&oq=rg&aqs=chrome..69i57j0l4j0i1i10j0j0i1i10j46i199i291j0.1759j0j7&sourceid=chrome&ie=UTF-8";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(google));
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_second);
        text = findViewById(R.id.textView2);
        button = findViewById(R.id.button2);
        Intent intent = getIntent();
        txt = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        text.setText(txt);
        button.setOnClickListener(mButtonListener);
    }
}
