package com.elegion.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.elegion.myfirstapplication.model.User;

public class ProfileActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_KEY";

    private TextView mEmail;
    private TextView mName;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        mEmail = findViewById(R.id.tvEmail);
        mName = findViewById(R.id.tvName);

        Bundle bundle = getIntent().getExtras();
        mUser = (User) bundle.get(USER_KEY);
        mEmail.setText(mUser.getEmail());
        mName.setText(mUser.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionLogout:
                startActivity(new Intent(this, AuthActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
