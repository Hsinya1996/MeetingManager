package com.example.utaipei.meetingmanager.http.Api;

import com.example.utaipei.meetingmanager.http.Model.MemberModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by cindy on 2017/9/15.
 */

public interface MemberApi {
    @Headers("Content-Type: application/json")
    @GET("member")
    Call<List<MemberModel>> getCall();
}
