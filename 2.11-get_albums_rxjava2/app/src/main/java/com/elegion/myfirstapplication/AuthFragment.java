package com.elegion.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elegion.myfirstapplication.albums.AlbumsActivity;
import com.elegion.myfirstapplication.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthFragment extends Fragment {
    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid()) {
                Request request = new Request.Builder()
                        .url(BuildConfig.SERVER_URL.concat("/user"))
                        .build();

                OkHttpClient client = ApiUtils.getBasicAuthClient(
                        mEmail.getText().toString(),
                        mPassword.getText().toString(),
                        true);
                client.newCall(request).enqueue(new Callback() {
                    //используем Handler, чтобы показывать ошибки в Main потоке, т.к. наши коллбеки возвращаются в рабочем потоке
                    Handler mainHandler = new Handler(getActivity().getMainLooper());

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        mainHandler.post(() -> showMessage(R.string.request_error));
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                        mainHandler.post(() -> {
                            if (!response.isSuccessful()) {
                                //todo добавить полноценную обработку ошибок по кодам ответа от сервера и телу запроса
                                showMessage(R.string.auth_error);
                            } else {
                                try {
                                    Gson gson = new Gson();
                                    JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
                                    User user = gson.fromJson(json.get("data"), User.class);

                                    /*Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                                    startProfileIntent.putExtra(ProfileActivity.USER_KEY, user);
                                    startActivity(startProfileIntent);*/
                                    startActivity(new Intent(getActivity(), AlbumsActivity.class));
                                    getActivity().finish();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    private View.OnClickListener mOnRegisterClickListener = view -> getFragmentManager()
            .beginTransaction()
            .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
            .addToBackStack(RegistrationFragment.class.getName())
            .commit();

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

        mEmail = v.findViewById(R.id.etEmail);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.buttonEnter);
        mRegister = v.findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);
        mEmail.setOnFocusChangeListener(mOnEmailFocusChangeListener);

        return v;
    }
}
