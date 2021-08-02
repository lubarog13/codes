package com.elegion.myfirstapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.elegion.myfirstapplication.model.converter.DataConverterFactory;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.annotations.Nullable;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marat.taychinov
 */

public class ApiUtils {
    public static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class
    );

    private static OkHttpClient client;
    public static String credential;
    private static Retrofit retrofit;
    private static Gson gson;
    private static AcademyApi api;

    public static OkHttpClient getBasicAuthClient(String email, String password, boolean createNewInstance, boolean remakeCredential) {
        Log.e("password", password);
        if (createNewInstance || client == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.authenticator(new okhttp3.Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    Log.e("_1", client.toString());
                    Log.e("cred", credential);
                    Log.e("pass", Boolean.toString(remakeCredential));
                    if (credential.equals(response.request().header("Authorization"))) {
                    Log.e("error", response.request().header("Authorization"));
                    return null; // If we already failed with these credentials, don't retry.
                }
                    return response.request().newBuilder().header("Authorization", credential).build();

                }
            });
            if (!BuildConfig.BUILD_TYPE.contains("release")) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }

            client = builder.build();
        }
        return client;
    }
    public static void setBasicAuthClient(String email, String password) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.authenticator(new okhttp3.Authenticator() {
            @Nullable
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(email, password);
                Log.e("client", client.toString());
                Log.e("cred", credential);
                Log.e("pass", password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        });
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        client = builder.build();
    }

    public static Retrofit getRetrofit() {
        if (gson == null) {
            gson = new Gson();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    // need for interceptors
                    .client(getBasicAuthClient("", "", false, false))
                    .addConverterFactory(new DataConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static AcademyApi getApiService() {
        if (api == null) {
            api = getRetrofit().create(AcademyApi.class);
        }
        return api;
    }
}
