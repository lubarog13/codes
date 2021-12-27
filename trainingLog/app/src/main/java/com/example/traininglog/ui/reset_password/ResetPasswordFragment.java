package com.example.traininglog.ui.reset_password;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.traininglog.R;
import com.example.traininglog.common.BasePresenter;
import com.example.traininglog.common.PresenterFragment;
import com.example.traininglog.common.RefreshOwner;
import com.example.traininglog.ui.auth.AuthFragment;
import com.example.traininglog.ui.auth.MainActivity;
import com.example.traininglog.ui.registration.RegistrationFragment;

import java.io.IOException;
import java.io.InputStream;


public class ResetPasswordFragment extends PresenterFragment implements ResetPasswordView{
    private EditText mEmailField;
    @InjectPresenter
    ResetPasswordPresenter mPresenter;
    private RefreshOwner mRefreshOwner;

    @ProvidePresenter
    ResetPasswordPresenter providePresenter(){return new ResetPasswordPresenter();}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RefreshOwner) {
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmailField = view.findViewById(R.id.email);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView password = getActivity().findViewById(R.id.password);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Caveat-VariableFont_wght.ttf");
        password.setTypeface(typeFace);
        ImageView imageView = getActivity().findViewById(R.id.image);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("reset_password.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {
        }
        TextView registration = getActivity().findViewById(R.id.registrationButton);
        registration.setOnClickListener(view1 ->((MainActivity) getActivity()).changeFragment(RegistrationFragment.newInstance()));
        getActivity().findViewById(R.id.reset_button).setOnClickListener(view -> save());
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
    public void showError(Throwable throwable) {
        if(throwable!=null) Log.e("err", throwable.getMessage());
        Toast.makeText(getActivity(), "Ошибка подключения к серверу", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess() {
        ((MainActivity) getActivity()).changeFragment(AuthFragment.newInstance());
    }

    private void save() {
        if(mEmailField.getText()==null) {
            mEmailField.setError("Введите email");
            return;
        }
        String email = mEmailField.getText().toString();
        if(email.length()==0) {
            mEmailField.setError("Введите email");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailField.setError("Неверный формат email");
            return;
        }
        mPresenter.resetPassword(email);
    }

    @Override
    protected ResetPasswordPresenter getPresenter() {
        return mPresenter;
    }
}