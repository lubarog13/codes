package com.example.traininglog.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traininglog.R;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.api.APIKeyInterceptor;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.utils.ApiUtils;

public class MainActivity extends AppCompatActivity implements AuthView, SwipeRefreshLayout.OnRefreshListener, RefreshOwner, Refreshable {
    Button enter;
    EditText login;
    AuthPresenter mPresenter;
    EditText password;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enter = findViewById(R.id.login);
        login = findViewById(R.id.login_enter);
        password = findViewById(R.id.password_enter);
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        mPresenter = new AuthPresenter(this, sharedPreferences);
        mSwipeRefreshLayout = findViewById(R.id.auth_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.logIn();
            }
        });
    }


    @Override
    public void showSuccess(AuthUser user) {
        ApiUtils.token = user.getAuth_token();
        Toast.makeText(this, user.getAuth_token(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String why) {
        Toast.makeText(this, why, Toast.LENGTH_LONG).show();
    }

    @Override
    public AuthUser getUser() {
        if(login.getText()!=null && password.getText()!=null)
        return new AuthUser(login.getText().toString(), password.getText().toString());
        else return new AuthUser("String", "String");
    }

    @Override
    public void navigateHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showRefresh() {
        setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        setRefreshState(false);
    }

    @Override
    public void onRefresh() {
        this.onRefreshData();
    }

    @Override
    public void setRefreshState(boolean refreshing) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }

    @Override
    public void onRefreshData() {

    }
}