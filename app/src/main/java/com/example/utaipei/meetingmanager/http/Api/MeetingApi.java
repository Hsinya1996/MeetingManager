package com.example.utaipei.meetingmanager.http.Api;


import com.example.utaipei.meetingmanager.http.Model.MeetingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by cindy on 2017/9/26.
 */

public interface MeetingApi {
    @Headers("Content-Type: application/json")
    @GET("meeting")
    Call<List<MeetingModel>> getCall();
}

