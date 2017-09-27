package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.MeetingroomModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by cindy on 2017/9/26.
 */

public interface MeetingroomApi {
    @Headers("Content-Type: application/json")
    @GET("meetingroom")
    Call<List<MeetingroomModel>> getCall();
}
