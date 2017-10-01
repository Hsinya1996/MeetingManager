package com.example.utaipei.meetingmanager.http.Model;

import java.util.List;

/**
 * Created by cindy on 2017/9/26.
 */

public class MeetingModel {
    private int meeting_id;
    private String meeting_name;
    private String meetingroom_id;
    private String meeting_date;
    private String meeting_starttime;
    private String meeting_endtime;
    private String administrator;
    private String organizer;
    private String speaker;
    private String participants;
    private String content;
    private int fare;
    private String address;
    private int attendance;
    private List<FeedbackModel> feedback;

    public void setMeetingId(int meeting_id){ this.meeting_id = meeting_id; }
    public int getMeetingId(){ return meeting_id; }

    public void setMeetingName(String meeting_name){ this.meeting_name = meeting_name; }
    public String getMeetingName(){ return meeting_name; }

    public void setMeetingroomId(String meetingroom_id){ this.meetingroom_id = meetingroom_id; }
    public String getMeetingroomId(){ return meetingroom_id; }

    public void setMeetingDate(String meeting_date){ this.meeting_date = meeting_date; }
    public String getMeetingDate(){ return meeting_date; }

    public void setMeetingStarttime(String meeting_starttime){ this.meeting_starttime = meeting_starttime; }
    public String getMeetingStarttime(){ return meeting_starttime; }

    public void setMeetingEndtime(String meeting_endtime){ this.meeting_endtime = meeting_endtime; }
    public String getMeetingEndtime(){ return meeting_endtime; }

    public void setAdministrator(String administrator){ this.administrator = administrator; }
    public String getAdministrator(){ return administrator; }

    public void setOrganizer(String organizer){ this.organizer = organizer; }
    public String getOrganizer(){ return organizer; }

    public void setSpeaker(String speaker){ this.speaker = speaker; }
    public String getSpeaker(){ return speaker; }

    public void setParticipants(String participants){ this.participants = participants; }
    public String getParticipants(){ return participants; }

    public void setContent(String content){ this.content = content; }
    public String getContent(){ return content; }

    public void setFare(int fare){ this.fare = fare; }
    public int getFare(){ return fare; }

    public void setAddress(String address){ this.address = address; }
    public String getAddress(){ return address; }

    public void setAttendance(int attendance){ this.attendance = attendance; }
    public int getAttendance(){ return attendance; }

    public void setFeedback(List<FeedbackModel> feedback) {
        this.feedback = feedback;
    }
    public List<FeedbackModel> getFeedback() {
        return feedback;
    }
}
