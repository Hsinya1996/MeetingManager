package com.example.utaipei.meetingmanager.http;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dai on 2017/4/8.
 */

public abstract class ApiCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        int httpCode = response.code();
        if (httpCode < 200 || httpCode >= 400) {
            onApiFailure(response);
            return;
        }
        onApiSuccess(response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onNoResponse(t);
    }

    public abstract void onApiSuccess(Response<T> response);

    public abstract void onApiFailure(Response<T> response);

    public abstract void onNoResponse(Throwable throwable);

    //public abstract void onTokenExpired();
}
