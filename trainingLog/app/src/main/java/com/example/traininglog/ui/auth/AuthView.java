package com.example.traininglog.ui.auth;

import android.content.SharedPreferences;

import com.example.traininglog.common.BaseView;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;

public interface AuthView extends BaseView {
    void showSuccess( AuthUser user);
    void navigateHome();
    void saveUser(User user);
}
