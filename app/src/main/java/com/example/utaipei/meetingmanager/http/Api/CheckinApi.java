package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.CheckinModel;
import com.example.utaipei.meetingmanager.http.Model.PositionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by cindy on 2017/9/25.
 */

public interface CheckinApi {
    @POST("checkinApi")
    Call<CheckinModel> postCheckin(@Body CheckinModel body);
}
