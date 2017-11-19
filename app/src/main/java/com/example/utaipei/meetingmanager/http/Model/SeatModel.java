package com.example.utaipei.meetingmanager.http.Model;

/**
 * Created by cindy on 2017/11/19.
 */

public class SeatModel {
    private String room_id;
    private int seat_xid;
    private int seat_yid;
    private String seat_ssid;
    private int wifi_level;
    private String mac_address;

    public void setRoomId(String room_id){ this.room_id = room_id; }
    public String getRoomId(){ return room_id; }

    public void setSeatXid(int seat_xid){ this.seat_xid = seat_xid; }
    public int getSeatXid(){ return seat_xid; }

    public void setSeatYid(int seat_yid){ this.seat_yid = seat_yid; }
    public int getSeatYid(){ return seat_yid; }

    public void setSeatSsid(String seat_ssid){ this.seat_ssid = seat_ssid; }
    public String getSeatSsid(){ return seat_ssid; }

    public void setWifiLevel(int wifi_level){ this.wifi_level = wifi_level; }
    public int getWifiLevel(){ return wifi_level; }

    public void setMacAddress(String mac_address){ this.mac_address = mac_address; }
    public String getMacAddress(){ return mac_address; }
}
