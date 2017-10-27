package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.PositionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by cindy on 2017/9/25.
 */

public interface PositionApi {
    @PUT("positionUpdate/{email}/{mac}")
    Call<PositionModel> postPositions(@Path("email") String email,@Path("mac") String mac,@Body PositionModel body);
}
