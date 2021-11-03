package com.example.traininglog.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.data.model.User;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.utils.ApiUtils;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class AuthFragment extends PresenterFragment implements AuthView, Refreshable  {

    private RefreshOwner mRefreshOwner;
    private SharedPreferences sp;
    EditText password;
    Button enter;
    EditText login;
    @InjectPresenter
    AuthPresenter mPresenter;

    @ProvidePresenter
    AuthPresenter providePresenter() {
        return new AuthPresenter();
    }

    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            Log.e("m", "mRefreshOwner");
            mRefreshOwner = ((RefreshOwner) context);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        enter = getActivity().findViewById(R.id.login);
        login = getActivity().findViewById(R.id.login_enter);
        password = getActivity().findViewById(R.id.password_enter);
        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        mPresenter.login = RxTextView.textChanges(login).map(CharSequence::toString);
        mPresenter.password=RxTextView.textChanges(password).map(CharSequence::toString);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.logIn();
            }
        });
        onRefreshData();
    }

    @Override
    protected AuthPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onRefreshData() {

    }

    @Override
    public void showSuccess(AuthUser user) {
        ApiUtils.token = user.getAuth_token();
        Toast.makeText(getActivity(), user.getAuth_token(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String why) {
        Toast.makeText(getActivity(), why, Toast.LENGTH_LONG).show();
        Log.e("http", why);
    }


    @Override
    public void navigateHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showRefresh() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshOwner.setRefreshState(false);
    }
    @Override
    public void saveUser(User user) {
        Log.e("user", user.getEmail());
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", user.getId());
        editor.putString("first_name", user.getFirst_name());
        editor.putString("last_name", user.getLast_name());
        editor.putString("second_name", user.getSecond_name());
        editor.putString("email", user.getEmail());
        editor.putString("sex", user.getSex());
        editor.putString("date_birth", user.getDate_birth().toString());
        editor.putString("username", user.getUsername());
        editor.putBoolean("is_coach", user.Is_coach());
        editor.putString("token", ApiUtils.token);
        ApiUtils.user_id = user.getId();
        editor.apply();
        this.navigateHome();
    }
}
