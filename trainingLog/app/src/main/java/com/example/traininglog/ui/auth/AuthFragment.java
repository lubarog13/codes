package com.example.traininglog.ui.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.common.Refreshable;
import com.example.traininglog.data.model.AuthUser;
import com.example.traininglog.ui.HomeActivity;
import com.example.traininglog.ui.registration.RegistrationFragment;
import com.example.traininglog.ui.reset_password.ResetPasswordFragment;
import com.example.traininglog.utils.ApiUtils;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AuthFragment extends PresenterFragment implements AuthView, Refreshable {

    private RefreshOwner mRefreshOwner;
    private SharedPreferences sp;
    private EditText password;
    private Button enter;
    private EditText login;
    private TextView mErrorText;
    private TextView mSystem;
    private TextView mRegistration;
    private TextView mResetPassword;
    private TextView mAsGuest;
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
        mPresenter.setSharedPreferences(sp);
//        if(sp.contains("id")){
//            mPresenter.logIn();
//        }
        mPresenter.login = RxTextView.textChanges(login).map(CharSequence::toString);
        mPresenter.password = RxTextView.textChanges(password).map(CharSequence::toString);
        mErrorText = getActivity().findViewById(R.id.error_text);
        mSystem = getActivity().findViewById(R.id.system);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Caveat-VariableFont_wght.ttf");
        mSystem.setTypeface(typeFace);
        mRegistration = getActivity().findViewById(R.id.reg);
        mRegistration.setOnClickListener(view -> this.changeFragment(RegistrationFragment.class));
        mResetPassword = getActivity().findViewById(R.id.set_pass);
        mResetPassword.setOnClickListener(view -> this.changeFragment(ResetPasswordFragment.class));
        mAsGuest = getActivity().findViewById(R.id.incognito);
        mAsGuest.setOnClickListener(view -> {
            login.setText("default");
            password.setText("guest_0_0");
            mPresenter.logIn();
        });
        ImageView imageView = getActivity().findViewById(R.id.auth_image);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("iPhone SE - 1.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {

        }
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void showSuccess(AuthUser user) {
        ApiUtils.token = user.getAuth_token();
        this.mErrorText.setText("Отлично! Вы вошли!");
        this.mErrorText.setTextColor(R.color.colorDivider);
        mPresenter.getUser();
    }

    @Override
    public void showError(Throwable throwable) {
        if (Objects.requireNonNull(throwable.getMessage()).contains("400"))
            this.mErrorText.setText(Html.fromHtml(getActivity().getResources()
                    .getString(R.string.hall_name, "Неверный логин или пароль"), Html.FROM_HTML_MODE_LEGACY));
        else if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
            this.mErrorText.setText(Html.fromHtml(getActivity().getResources()
                    .getString(R.string.hall_name, "Ошибка сервера или интернет-соединения"), Html.FROM_HTML_MODE_LEGACY));
        } else this.mErrorText.setText(Html.fromHtml(getActivity().getResources()
                .getString(R.string.hall_name, "Произошла ошибка"), Html.FROM_HTML_MODE_LEGACY));
        Log.e("http", throwable.getMessage());
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

    private void changeFragment(Class fragmentClass){
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((MainActivity) getActivity()).changeFragment(fragment);
    }
}
