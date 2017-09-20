package com.example.utaipei.meetingmanager.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
/*
 * Created by Dai on 2017/4/14.
 */

public class HttpClient {
    private static final int CONNECT_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;

    private static String token = "";
    private static OkHttpClient okHttpClient;

    private HttpClient() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            RequestTokenInterceptor interceptor = new RequestTokenInterceptor();

            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }

    public static void setToken(String token) {
        HttpClient.token = token;
    }

    public static String getToken() {
        return HttpClient.token;
    }
}
