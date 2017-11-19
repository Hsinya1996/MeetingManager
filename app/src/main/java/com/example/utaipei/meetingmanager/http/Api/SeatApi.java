package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.SeatModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by cindy on 2017/11/19.
 */

public interface SeatApi {
    @PUT("seatingUpdate/{roomId}/{mac}/{x}/{y}")
    Call<SeatModel> postSeats(@Path("roomId") String roomId, @Path("mac") String mac, @Path("x") String x, @Path("y") String y, @Body SeatModel body);
}
