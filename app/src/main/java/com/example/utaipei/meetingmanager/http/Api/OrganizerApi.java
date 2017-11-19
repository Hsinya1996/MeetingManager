package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.OrganizerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by cindy on 2017/11/19.
 */

public interface OrganizerApi {
    @Headers("Content-Type: application/json")
    @GET("organizer")
    Call<List<OrganizerModel>> getCall();
}
