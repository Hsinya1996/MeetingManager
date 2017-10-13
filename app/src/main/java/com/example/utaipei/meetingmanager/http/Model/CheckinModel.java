package com.example.utaipei.meetingmanager.http.Model;

/**
 * Created by cindy on 2017/9/25.
 */

public class CheckinModel {
    private String member_email;
    private int meeting_id;
    private String meetingroom_id;
    private String login_time;
    private String logout_time;
    private int seat_id;

    public void setMemberEmail(String member_email){ this.member_email = member_email; }
    public String getMemberEmail(){ return member_email;}

    public void setMeetingId(int meeting_id){ this.meeting_id = meeting_id; }
    public int getMeetingId(){ return meeting_id; }

    public void setMeetingroomId(String meetingroom_id){ this.meetingroom_id = meetingroom_id; }
    public String getMeetingroomId(){ return meetingroom_id;}

    public void setLoginTime(String login_time){ this.login_time = login_time; }
    public String getLoginTime(){ return login_time;}

    public void setLogoutTime(String logout_time){ this.logout_time = logout_time; }
    public String getLogoutTime(){ return logout_time;}

    public void setSeatId(int seat_id){ this.seat_id = seat_id; }
    public int getSeatId(){ return seat_id; }
}
