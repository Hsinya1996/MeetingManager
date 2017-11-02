package com.example.utaipei.meetingmanager.http.Model;

/**
 * Created by cindy on 2017/9/25.
 */

public class PositionModel {
    private String member_email;
    private String meetingroom_id;
    private String current_ssid;
    private String mac_address;
    private int wifi_level;
    private String wifi_time;

    public void setMemberEmail(String member_email){ this.member_email = member_email; }
    public String getMemberEmail(){ return member_email; }

    public void setMeetingroomId(String meetingroom_id){ this.meetingroom_id = meetingroom_id; }
    public String getMeetingroomId(){ return meetingroom_id; }

    public void setCurrentSsid(String current_ssid){ this.current_ssid = current_ssid; }
    public String getCurrentSsid(){ return current_ssid; }

    public void setMacAddress(String mac_address){ this.mac_address = mac_address; }
    public String getMacAddress(){ return mac_address; }

    public void setWifiLevel(int wifi_level){ this.wifi_level = wifi_level; }
    public int getWifiLevel(){ return wifi_level; }

    public void setWifiTime(String wifi_time){ this.wifi_time = wifi_time; }
    public String getWifiTime(){ return wifi_time; }
}
