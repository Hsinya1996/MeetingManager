package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.FeedbackModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by cindy on 2017/9/30.
 */

public interface FeedbackApi {
    @PUT("feedbackUpdate/{email}/{meetingId}")
    Call<FeedbackModel> postFeedback(@Path("email") String email, @Path("meetingId") String meetingId, @Body FeedbackModel body);
}
