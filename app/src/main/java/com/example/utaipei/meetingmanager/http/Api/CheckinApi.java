package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.CheckinModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by cindy on 2017/9/25.
 */

public interface CheckinApi {
    @PUT("checkinUpdate/{email}/{meetingId}")
    Call<CheckinModel> postCheckin(@Path("email") String email,@Path("meetingId") String meetingId,@Body CheckinModel body);
}
