package com.example.utaipei.meetingmanager.http;


import com.example.utaipei.meetingmanager.http.Api.MemberApi;
import com.example.utaipei.meetingmanager.http.Api.PositionApi;

/**
 * Created by Dai on 2017/4/8.
 */

public class ServiceFactory {
    private static MemberApi memberApi;
    private static PositionApi positionApi;

    public static MemberApi getMemberApi() {
        if (memberApi == null) {
            memberApi = ApiClient.getInstance().createService(MemberApi.class);
        }
        return memberApi;
    }

    public static PositionApi getPositionApi() {
        if (positionApi == null) {
            positionApi = ApiClient.getInstance().createService(PositionApi.class);
        }
        return positionApi;
    }

}
