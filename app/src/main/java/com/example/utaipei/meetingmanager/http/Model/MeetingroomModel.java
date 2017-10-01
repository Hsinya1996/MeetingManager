package com.example.utaipei.meetingmanager.http.Model;

/**
 * Created by cindy on 2017/9/26.
 */

public class MeetingroomModel {
    private String room_id;
    private String meetingroom_ssid;
    private String mac_address;

    public void setRoomId(String room_id){ this.room_id = room_id; }
    public String getRoomId(){ return room_id; }

    public void setMeetingroomSsid(String meetingroom_ssid){ this.meetingroom_ssid = meetingroom_ssid; }
    public String getMeetingroomSsid(){ return meetingroom_ssid; }

    public void setMacAddress(String mac_address){ this.mac_address = mac_address; }
    public String getMacAddress(){ return mac_address; }
}
