package com.elegion.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elegion.myfirstapplication.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthFragment extends Fragment {
    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    private ArrayAdapter<String> mEmailedUsersAdapter;

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(isEmailValid() && isPasswordValid()) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String credential = Credentials.basic(email, password);
                Log.d("AAAAAAAAA", credential);
                ApiUtils.getApiService().user(credential).enqueue(
                        new retrofit2.Callback<Object>() {
                            //используем Handler, чтобы показывать ошибки в Main потоке, т.к. наши коллбеки возвращаются в рабочем потоке
                            Handler mainHandler = new Handler(getActivity().getMainLooper());

                            @Override
                            public void onResponse(retrofit2.Call<Object> call, final retrofit2.Response<Object> response) {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!response.isSuccessful()) {
                                            //todo добавить полноценную обработку ошибок по кодам ответа от сервера и телу запроса
                                            showMessage(R.string.auth_error);
                                        } else {
                                            Gson gson = new Gson();
                                            JsonObject json = gson.fromJson(response.body().toString(), JsonObject.class);
                                            User user = gson.fromJson(json.get("data"), User.class);

                                            Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                                            startProfileIntent.putExtra(ProfileActivity.USER_KEY, user);
                                            startActivity(startProfileIntent);
                                            getActivity().finish();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(retrofit2.Call<Object> call, Throwable t) {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showMessage(R.string.request_error);
                                    }
                                });
                            }
                        });

            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName())
                    .commit();
        }
    };

    private View.OnFocusChangeListener mOnEmailFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                mEmail.showDropDown();
            }
        }
    };

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mEmail.getText())
                && Patterns.EMAIL_ADDRESS.matcher(mEmail.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_auth, container, false);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        mEmail = v.findViewById(R.id.etEmail);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.buttonEnter);
        mRegister = v.findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);
        mEmail.setOnFocusChangeListener(mOnEmailFocusChangeListener);

        mEmailedUsersAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                mSharedPreferencesHelper.getSuccessEmails()
        );
        mEmail.setAdapter(mEmailedUsersAdapter);

        return v;
    }
}
