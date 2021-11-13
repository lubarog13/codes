package com.elegion.myfirstapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marat.taychinov
 */

public class ApiUtils {
    private static OkHttpClient client;
    private static Retrofit retrofit;
    private static Gson gson;
    private static AcademyApi api;

    public static OkHttpClient getBasicAuthClient(final String email, final String password, boolean createNewInstance) {
        if (createNewInstance || client == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.authenticator(new Authenticator() {
                @Nullable
                @Override
                public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
                    String credential = Credentials.basic(email, password);
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

    public static Retrofit getRetrofit() {
        if (gson == null) {
            gson = new Gson();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    // need for interceptors
                    .client(getBasicAuthClient("", "", false))
                    .addConverterFactory(GsonConverterFactory.create(gson))
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
