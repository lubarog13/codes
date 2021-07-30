package com.elegion.myfirstapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elegion.myfirstapplication.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

public class RegistrationFragment extends Fragment {
    private EditText mEmail;
    private EditText mName;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mRegistration;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    private View.OnClickListener mOnRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPassword.setBackgroundColor(getResources().getColor(R.color.white));
            mPasswordAgain.setBackgroundColor(getResources().getColor(R.color.white));
            mEmail.setBackgroundColor(getResources().getColor(R.color.white));
            mName.setBackgroundColor(getResources().getColor(R.color.white));
            if (isInputValid()) {
                User user = new User(
                        mEmail.getText().toString(),
                        mName.getText().toString(),
                        mPassword.getText().toString());

                ApiUtils.getApiService().registration(user).enqueue(
                        new retrofit2.Callback<Void>() {
                            //используем Handler, чтобы показывать ошибки в Main потоке, т.к. наши коллбеки возвращаются в рабочем потоке
                            Handler mainHandler = new Handler(getActivity().getMainLooper());
                            @Override
                            public void onResponse(retrofit2.Call<Void> call, final retrofit2.Response<Void> response) {
                                mainHandler.post(() -> {
                                    if(response.code()==400) {
                                        Toast.makeText(getActivity(), "Ошибка валидации", Toast.LENGTH_LONG).show();
                                        try {
                                            String errors = response.errorBody().string();
                                            if (errors.contains("password")) {
                                                mPassword.setError("Короткий пароль");
                                                mPassword.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                            }
                                            if (errors.contains("email")) {
                                                mEmail.setError("Ошибка email");
                                                mEmail.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                            }
                                            if (errors.contains("name")) {
                                                mName.setError("Ошибка имени");
                                                mName.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else if (response.code()==500) {
                                        //todo добавить полноценную обработку ошибок по кодам ответа от сервера и телу запроса
                                        Toast.makeText(getActivity(), "Внутренняя ошибка сервера", Toast.LENGTH_LONG).show();
                                    } else {
                                        showMessage(R.string.registration_success);
                                        getFragmentManager().popBackStack();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                                mainHandler.post(() -> showMessage(R.string.request_error));
                            }
                        });
            } else {
                if(!isEmailValid(mEmail.getText().toString())) {
                    String error = TextUtils.isEmpty(mEmail.getText())? "Пустой email": "Неверный email";
                    mEmail.setError(error);
                    mEmail.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                if (TextUtils.isEmpty(mName.getText())) {
                    mName.setError("Имя не введено");
                    mName.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                if (!isPasswordsValid()) {
                    String error = TextUtils.isEmpty(mPassword.getText())? "Пустой пароль": "Не соответствует пароль";
                    mPassword.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mPassword.setError(error);
                    mPasswordAgain.setError(error);
                    mPasswordAgain.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                showMessage(R.string.input_error);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_registration, container, false);

        mEmail = view.findViewById(R.id.etEmail);
        mName = view.findViewById(R.id.etName);
        mPassword = view.findViewById(R.id.etPassword);
        mPasswordAgain = view.findViewById(R.id.tvPasswordAgain);
        mRegistration = view.findViewById(R.id.btnRegistration);

        mRegistration.setOnClickListener(mOnRegistrationClickListener);

        return view;
    }

    private boolean isInputValid() {
        return isEmailValid(mEmail.getText().toString())
                && !TextUtils.isEmpty(mName.getText())
                && isPasswordsValid();
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordsValid() {
        String password = mPassword.getText().toString();
        String passwordAgain = mPasswordAgain.getText().toString();

        return password.equals(passwordAgain)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain);
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

}
