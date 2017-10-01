package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.FeedbackModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by cindy on 2017/9/30.
 */

public interface FeedbackApi {
    @POST("feedbackApi")
    Call<FeedbackModel> postFeedback(@Body FeedbackModel body);
}
