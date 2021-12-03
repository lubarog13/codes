package com.example.traininglog.utils;

import com.example.traininglog.BuildConfig;
import com.example.traininglog.data.api.APIKeyInterceptor;
import com.example.traininglog.data.api.EsheduleApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class ApiUtils {

    public static String token;
    public static int user_id;
    public static int coach_id;

    public static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class

    );
    private static OkHttpClient sClient;
    private static Retrofit sRetrofit;
    private static Gson sGson;
    private static EsheduleApi sApi;

    private static OkHttpClient getClient() {
        if (sClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.addInterceptor(new APIKeyInterceptor());
            if (!BuildConfig.BUILD_TYPE.contains("release")) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            sClient = builder.build();
        }
        return sClient;
    }

    private static Retrofit getRetrofit() {
        if (sGson == null) {
            sGson = new Gson();
        }
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    // need for interceptors
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(sGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static EsheduleApi getApiService() {
        if (sApi == null) {
            sApi = getRetrofit().create(EsheduleApi.class);
        }
        return sApi;
    }

    public static EsheduleApi getsApiServiceForEditWithNulls() {
        return getCustomRetrofit().create(EsheduleApi.class);
    }

    private static Retrofit getCustomRetrofit() {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                // need for interceptors
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
