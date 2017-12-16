package com.example.utaipei.meetingmanager;

import android.widget.Toast;

/**
 * Created by cindy on 2017/12/14.
 */

public class WifiAverageLevel {
    private String ssid;
    private String macAddress;
    private int level;
    private int min=0;
    private int max=-100;
    private int count=0;

    public void setSsid(String ssid){ this.ssid = ssid; }
    public String getSsid(){ return ssid; }

    public void setMacAddress(String macAddress){ this.macAddress = macAddress; }
    public String getMacAddress(){ return macAddress; }

    public void setLevel(int level){this.level = level; }
    public int getLevel(){ return level;}

    public void setMin(int min){ this.min=min; }
    public int getMin(){ return min; }

    public void setMax(int max){ this.max=max; }
    public int getMax(){ return max; }

    public void setCount(int count){ this.count=count; }
    public int getCount(){ return count; }

    public void addCount(){ count++; }

    public void judgeLevel(int value){
        if(value<min){
            min=value;
        }
        if(value>max){
            max=value;
        }
    }

    public void addLevel(int level){ this.level+=level; }

    public int meanLevel(){
        float mean = (float) (((level-min-max)*1.0)/(count-2));
        int n = (level-min-max)/(count-2);
        mean = Math.abs(mean-n);
        if((mean-0.49)>=0.001){
            n-=1;
        }
        return n;
    }

}
