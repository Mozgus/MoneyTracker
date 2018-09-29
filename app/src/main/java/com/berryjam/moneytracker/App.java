package com.berryjam.moneytracker;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.berryjam.moneytracker.domain.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static final String PREFERENCES_SESSION = "session";
    private static final String KEY_AUTH_TOKEN = "auth-token";

    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor.Level level = BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(level);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthInterceptor())
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://android.loftschool.com/basic/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(getAuthToken());
    }

    public void deleteAuthToken() {
        getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).edit().remove(KEY_AUTH_TOKEN).apply();
    }

    public void saveAuthToken(String token) {
        getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).edit().putString(KEY_AUTH_TOKEN, token).apply();
    }

    public String getAuthToken() {
        return getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).getString(KEY_AUTH_TOKEN, "");
    }

    private class AuthInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String authToken = getAuthToken();
            HttpUrl url = originalRequest.url().newBuilder().addQueryParameter("auth-token", authToken).build();
            return chain.proceed(originalRequest.newBuilder().url(url).build());
        }

    }

}
