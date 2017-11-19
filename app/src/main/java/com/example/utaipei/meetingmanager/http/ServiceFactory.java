package com.example.utaipei.meetingmanager.http;


import com.example.utaipei.meetingmanager.http.Api.CheckinApi;
import com.example.utaipei.meetingmanager.http.Api.FeedbackApi;
import com.example.utaipei.meetingmanager.http.Api.MeetingApi;
import com.example.utaipei.meetingmanager.http.Api.MeetingroomApi;
import com.example.utaipei.meetingmanager.http.Api.MemberApi;
import com.example.utaipei.meetingmanager.http.Api.OrganizerApi;
import com.example.utaipei.meetingmanager.http.Api.PositionApi;
import com.example.utaipei.meetingmanager.http.Api.SeatApi;
import com.example.utaipei.meetingmanager.http.Model.CheckinModel;
import com.example.utaipei.meetingmanager.http.Model.FeedbackModel;

/**
 * Created by Dai on 2017/4/8.
 */

public class ServiceFactory {
    private static MemberApi memberApi;
    private static PositionApi positionApi;
    private static MeetingroomApi meetingroomApi;
    private static CheckinApi checkinApi;
    private static MeetingApi meetingApi;
    private static FeedbackApi feedbackApi;
    private static OrganizerApi organizerApi;
    private static SeatApi seatApi;

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

    public static MeetingroomApi getMeetingroomApi() {
        if (meetingroomApi == null) {
            meetingroomApi = ApiClient.getInstance().createService(MeetingroomApi.class);
        }
        return meetingroomApi;
    }

    public static CheckinApi getCheckinApi() {
        if (checkinApi == null) {
            checkinApi = ApiClient.getInstance().createService(CheckinApi.class);
        }
        return checkinApi;
    }

    public static MeetingApi getMeetingApi() {
        if (meetingApi == null) {
            meetingApi = ApiClient.getInstance().createService(MeetingApi.class);
        }
        return meetingApi;
    }

    public static FeedbackApi getFeedbackApi() {
        if (feedbackApi == null) {
            feedbackApi = ApiClient.getInstance().createService(FeedbackApi.class);
        }
        return feedbackApi;
    }

    public static OrganizerApi getOrganizerApi() {
        if (organizerApi == null) {
            organizerApi = ApiClient.getInstance().createService(OrganizerApi.class);
        }
        return organizerApi;
    }

    public static SeatApi getSeatApi() {
        if (seatApi == null) {
            seatApi = ApiClient.getInstance().createService(SeatApi.class);
        }
        return seatApi;
    }
}
