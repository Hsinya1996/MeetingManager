package com.example.utaipei.meetingmanager.http.Model;

/**
 * Created by cindy on 2017/9/30.
 */

public class FeedbackModel {
    private int meeting_id;
    private String member_email;
    private int time_feedback;
    private int location_feedback;
    private int itinerary_feedback;
    private String suggestions;

    public void setMeetingId(int meeting_id){ this.meeting_id = meeting_id; }
    public int getMeetingId(){ return meeting_id; }

    public void setMemberEmail(String member_email){ this.member_email = member_email; }
    public String getMemberEmail(){ return member_email; }

    public void setTimeFeedback(int time_feedback){ this.time_feedback = time_feedback; }
    public int getTimeFeedback(){ return time_feedback; }

    public void setLocationFeedback(int location_feedback){ this.location_feedback = location_feedback; }
    public int getLocationFeedback(){ return location_feedback; }

    public void setItineraryFeedback(int itinerary_feedback){ this.itinerary_feedback = itinerary_feedback; }
    public int getItineraryFeedback(){ return itinerary_feedback; }

    public void setSuggestions(String suggestions){ this.suggestions = suggestions; }
    public String getSuggestions(){ return suggestions; }
}
