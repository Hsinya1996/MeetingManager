package com.example.utaipei.meetingmanager.http;

import android.support.constraint.BuildConfig;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dai on 2017/4/14.
 */

public class RequestTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        /*String token = HttpClient.getToken() == null ? "" : HttpClient.getToken();
        Request newRequest = request.newBuilder().addHeader("AI-Bike-Token", token).build();*/
        Response response = chain.proceed(request);
        //Peek the http response for debugging
        if (BuildConfig.DEBUG) {
            Log.d("HttpResponse", response.peekBody(1024).string());
        }
        return response;
    }
}
