package com.example.traininglog.data.api;

import androidx.annotation.NonNull;

import com.example.traininglog.utils.ApiUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class APIKeyInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url().newBuilder()
                .build();
        if(ApiUtils.token==null) request = request.newBuilder().url(httpUrl).build();
        else request = request.newBuilder().addHeader("Authorization", "Token " + ApiUtils.token).url(httpUrl).build();

        return chain.proceed(request);
    }
}
