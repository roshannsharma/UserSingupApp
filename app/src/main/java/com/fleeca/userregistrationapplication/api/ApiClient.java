package com.fleeca.userregistrationapplication.api;


import static com.fleeca.userregistrationapplication.utils.ApiConstant.BASE_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static ApiService getApiService(String mToken) {
        // Interceptor to add headers

        Interceptor headerInterceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .addHeader("AUTHTOKEN", mToken)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("referer", "https://staging.gcp.woliba.io/")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };

        // Logging Interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // OkHttpClient with timeouts and interceptors
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.MINUTES)
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(headerInterceptor)
                .build();

        // GSON with lenient parsing
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        // Api Interface creation
        return retrofit.create(ApiService.class);
    }

}

