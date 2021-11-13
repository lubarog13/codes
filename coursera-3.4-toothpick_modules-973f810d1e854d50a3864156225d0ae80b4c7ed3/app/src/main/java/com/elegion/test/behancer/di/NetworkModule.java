package com.elegion.test.behancer.di;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.data.api.ApiKeyInterceptor;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;

/**
 * Created by tanchuev on 23.04.2018.
 */

public class NetworkModule extends Module {

    private final Gson mGson = new Gson();
    private final OkHttpClient mOkHttpClient = getClient();
    private final Retrofit mRetrofit = getRetrofit();

    public NetworkModule() {
        bind(Gson.class).toInstance(mGson);
        bind(OkHttpClient.class).toInstance(mOkHttpClient);
        bind(Retrofit.class).toInstance(mRetrofit);
        bind(BehanceApi.class).toInstance(getApiService());
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new ApiKeyInterceptor());
        if (!BuildConfig.BUILD_TYPE.contains("release")) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                // need for interceptors
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public BehanceApi getApiService() {
        return mRetrofit.create(BehanceApi.class);
    }
}
