package com.example.utaipei.meetingmanager.http;


import com.example.utaipei.meetingmanager.http.Api.ExampleApi;
import com.example.utaipei.meetingmanager.http.Api.MemberApi;

/**
 * Created by Dai on 2017/4/8.
 */

public class ServiceFactory {
    private static MemberApi memberApi;



    public static MemberApi getMemberApi() {
        if (memberApi == null) {
            memberApi = ApiClient.getInstance().createService(MemberApi.class);
        }
        return memberApi;
    }



}
