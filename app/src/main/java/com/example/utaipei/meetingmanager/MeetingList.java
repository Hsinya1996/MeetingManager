package com.example.utaipei.meetingmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utaipei.meetingmanager.http.Model.CheckinModel;
import com.example.utaipei.meetingmanager.http.Model.MeetingModel;
import com.example.utaipei.meetingmanager.http.Model.MeetingroomModel;
import com.example.utaipei.meetingmanager.http.Model.MemberModel;
import com.example.utaipei.meetingmanager.http.Model.PositionModel;
import com.example.utaipei.meetingmanager.http.ServiceFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cindy on 2017/7/11.
 */

public class MeetingList extends AppCompatActivity{
    private TextView tv,lname,ltime,lplace;
    private Button sign0ut,lcheck;
    private WifiManager wifiManager;
    private TextView sh;
    private List<ScanResult> wifiList;
    private WifiScanReceiver wifiReciever;
    private Timer timer;
    private View view;
    private LayoutInflater inflater;
    private LinearLayout ll;
    private String email,date,time,roomId=null;
    private final ArrayList<Integer> meetingId = new ArrayList<Integer>();
    private final ArrayList<String> roomIds = new ArrayList<String>();
    private final ArrayList<String> logintime = new ArrayList<String>();
    private final ArrayList<String> meetingroom = new ArrayList<String>();
    public ArrayList<Integer> meetingMap = new ArrayList<Integer>();
    public ArrayList<Integer> btnList = new ArrayList<Integer>();
    public ArrayList<String> roomMap = new ArrayList<String>();
    private int btnId;
    private int checkBtn = -1;
    private String login_time = null;
    private final ArrayList<Integer> XIds = new ArrayList<Integer>();
    private final ArrayList<Integer> YIds = new ArrayList<Integer>();
    private int xId,yId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_list);

        //title name
        tv = (TextView)findViewById(R.id.tv);
        SpannableString content = new SpannableString("今日會議");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv.setText(content);

        //member name
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        email = bundle.getString("email");
        sh = (TextView)findViewById(R.id.show);
        sh.setText("您好,"+name);

        //get date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new java.util.Date());

        //meeting list
        Context mContext = MeetingList.this;
        // 取得 LinearLayout 物件
        ll = (LinearLayout) findViewById(R.id.lists);
        // 將feedviews加入到 LinearLayout 中
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //開啟wifi
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()){
            timer = new Timer();
            timer.schedule(new scanTask(),0, 60000) ;
        }



        sign0ut = (Button)findViewById(R.id.signout);
        sign0ut.setOnClickListener(signoutListener);

    }

    //scan wifi lists
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        updateLists();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    private void updateLists() throws IOException {
        ll.removeAllViews();
        meetingId.clear();
        roomIds.clear();
        meetingroom.clear();
        logintime.clear();
        btnList.clear();
        meetingMap.clear();
        roomMap.clear();
        XIds.clear();;
        YIds.clear();
        btnId = 0;

        //get time
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        time = sdf2.format(new java.util.Date());

        //post wifi data
        String text = String.valueOf(wifiList.size());
        //Toast.makeText(MeetingList.this,text,Toast.LENGTH_SHORT).show();

        for(int i=0;i<wifiList.size();i++){
            PositionModel position = new PositionModel();
            position.setMeetingroomId(roomId);
            position.setMemberEmail(email);
            position.setCurrentSsid(wifiList.get(i).SSID);
            position.setWifiLevel(wifiList.get(i).level);
            position.setMacAddress(wifiList.get(i).BSSID);
            String wifitime = date+" "+time;
            position.setWifiTime(wifitime);
            String mac = wifiList.get(i).BSSID;
            final int k = i;

            ServiceFactory.getPositionApi().postPositions(email,mac,position).enqueue(new Callback<PositionModel>() {
                @Override
                public void onResponse(Call<PositionModel> call, Response<PositionModel> response) {
                    //Toast.makeText(MeetingList.this,"server post",Toast.LENGTH_SHORT).show();
                    if(k==wifiList.size()-1){
                        getMemberCheckin();
                    }
                }

                @Override
                public void onFailure(Call<PositionModel> call, Throwable t) {
                    //Toast.makeText(MeetingList.this,"fail",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    //get meetings which member sign up
    public void getMemberCheckin(){

        ServiceFactory.getMemberApi().getCall().enqueue(new Callback<List<MemberModel>>() {
            @Override
            public void onResponse(Call<List<MemberModel>> call, Response<List<MemberModel>> response) {
                int num = response.body().size();
                for(int i=0;i<num;i++){
                    String mem = response.body().get(i).getMemberEmail();
                    if(email.equals(mem)){
                        int sign = response.body().get(i).getCheckin().size();
                        for(int m=0;m<sign;m++){
                            meetingId.add(response.body().get(i).getCheckin().get(m).getMeetingId());
                            roomIds.add(response.body().get(i).getCheckin().get(m).getMeetingroomId());
                            logintime.add(response.body().get(i).getCheckin().get(m).getLoginTime());
                            XIds.add(response.body().get(i).getCheckin().get(m).getSeatXid());
                            YIds.add(response.body().get(i).getCheckin().get(m).getSeatYid());
                        }
                    }
                }
                //Toast.makeText(MeetingList.this,String.valueOf(roomIds.size()),Toast.LENGTH_SHORT).show();
                updateCheckin();
            }

            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {

            }
        });
    }

    public void updateCheckin(){
        if(checkBtn==-1){
            checkRoomWifi();
        }else{
            CheckinModel checkin = new CheckinModel();
            checkin.setMemberEmail(email);
            checkin.setMeetingId(checkBtn);
            checkin.setMeetingroomId(roomId);
            checkin.setLoginTime(login_time);
            checkin.setLogoutTime(time);
            for(int k=0;k<meetingId.size();k++){
                if(meetingId.get(k)==checkBtn){
                    xId = XIds.get(k);
                    yId = YIds.get(k);
                }
            }
            checkin.setSeatXid(xId);
            checkin.setSeatYid(yId);
            String id = String.valueOf(checkBtn);
            ServiceFactory.getCheckinApi().postCheckin(email,id,checkin).enqueue(new Callback<CheckinModel>() {
                @Override
                public void onResponse(Call<CheckinModel> call, Response<CheckinModel> response) {
                    //Toast.makeText(MeetingList.this,"success",Toast.LENGTH_SHORT).show();
                    checkRoomWifi();
                }

                @Override
                public void onFailure(Call<CheckinModel> call, Throwable t) {

                }
            });
        }
    }

    public void checkRoomWifi(){
        //get meetingroom information.
        ServiceFactory.getMeetingroomApi().getCall().enqueue(new Callback<List<MeetingroomModel>>() {
            @Override
            public void onResponse(Call<List<MeetingroomModel>> call, Response<List<MeetingroomModel>> response) {
                int n = response.body().size();
                for(int i=0;i<roomIds.size();i++){
                    for(int k=0;k<n;k++) {
                        if(roomIds.get(i).equals(response.body().get(k).getRoomId())){
                            for(int v=0;v<wifiList.size();v++){
                                if(wifiList.get(v).SSID.equals(response.body().get(k).getMeetingroomSsid()) && wifiList.get(v).BSSID.equals(response.body().get(k).getMacAddress()) ){
                                    //Toast.makeText(MeetingList.this,response.body().get(k).getRoomId(),Toast.LENGTH_SHORT).show();
                                    meetingroom.add(response.body().get(k).getRoomId());
                                }
                            }
                        }
                    }
                }
                //Toast.makeText(MeetingList.this,String.valueOf(meetingroom.size()),Toast.LENGTH_SHORT).show();
                getMeetingInfo();
            }

            @Override
            public void onFailure(Call<List<MeetingroomModel>> call, Throwable t) {

            }
        });


    }

    //get meeting information
    public void getMeetingInfo(){

        ServiceFactory.getMeetingApi().getCall().enqueue(new Callback<List<MeetingModel>>() {
            @Override
            public void onResponse(Call<List<MeetingModel>> call, Response<List<MeetingModel>> response) {
                int num = response.body().size();
                for(int i=0;i<meetingId.size();i++){
                    for(int m=0;m<num;m++){
                        if(meetingId.get(i)==response.body().get(m).getMeetingId()){
                            if(response.body().get(m).getMeetingDate().equals(date)){
                                view = inflater.inflate(R.layout.lvitem, null, true);
                                lname = (TextView)view.findViewById(R.id.name);
                                ltime =  (TextView)view.findViewById(R.id.time);
                                lplace = (TextView)view.findViewById(R.id.place);
                                lcheck = (Button)view.findViewById(R.id.check);
                                if(logintime.get(i)!=null){
                                    lcheck.setText("進場");
                                }
                                lname.setText(response.body().get(m).getMeetingName());
                                String[] st = response.body().get(m).getMeetingStarttime().split(":");
                                String[] et = response.body().get(m).getMeetingEndtime().split(":");
                                String tt = st[0]+":"+st[1]+"-"+et[0]+":"+et[1];
                                ltime.setText(tt);
                                final String room = response.body().get(m).getMeetingroomId();
                                lplace.setText(room);
                                String[] time2 = time.split(":");
                                //check time
                                if(Integer.valueOf(time2[0])<Integer.valueOf(et[0])){
                                    for(int j=0;j<meetingroom.size();j++){
                                        if(room.equals(meetingroom.get(j))){
                                            lcheck.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }else if(Integer.valueOf(time2[0])==Integer.valueOf(et[0])){
                                    if(Integer.valueOf(time2[1])<=Integer.valueOf(et[1])){
                                        for(int j=0;j<meetingroom.size();j++){
                                            if(room.equals(meetingroom.get(j))){
                                                lcheck.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                }
                                ll.addView(view);
                                lcheck.setOnClickListener(checkinListener);
                                lcheck.setId(btnId);
                                meetingMap.add(response.body().get(m).getMeetingId());
                                roomMap.add(room);
                                btnList.add(btnId);
                                btnId++;

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MeetingModel>> call, Throwable t) {

            }
        });
    }


    //time tack of 60's
    private class scanTask extends TimerTask {
        @Override
        public void run() {
            wifiList=null;
            wifiReciever = new WifiScanReceiver();
            registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            while(wifiList==null){
                wifiManager.startScan();
                SystemClock.sleep(800);
            }
            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    }

    // wifi receiver
    private final class WifiScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context c, Intent intent) {
            wifiList = wifiManager.getScanResults();
        }
    }


    //check-in action
    private Button.OnClickListener checkinListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Button delBtn =  (Button)v;
            int id = delBtn.getId();//獲取被點擊的按鈕的id
            //Toast.makeText(MeetingList.this, String.valueOf(btnMap.get(id)),Toast.LENGTH_SHORT).show();
            roomId = roomMap.get(id);
            checkBtn = meetingMap.get(id);
            //Toast.makeText(MeetingList.this,String.valueOf(checkBtn),Toast.LENGTH_SHORT).show();
            for(int i=0;i<meetingId.size();i++){
                if(meetingId.get(i)==checkBtn){
                    login_time = logintime.get(i);
                }
            }

            if(login_time==null){
                CheckinModel checkin = new CheckinModel();
                checkin.setMemberEmail(email);
                checkin.setMeetingId(checkBtn);
                checkin.setMeetingroomId(roomId);
                SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm:ss");
                login_time = sdf4.format(new java.util.Date());
                checkin.setLoginTime(login_time);
                checkin.setLogoutTime(login_time);
                String ids = String.valueOf(checkBtn);
                ServiceFactory.getCheckinApi().postCheckin(email,ids,checkin).enqueue(new Callback<CheckinModel>() {
                    @Override
                    public void onResponse(Call<CheckinModel> call, Response<CheckinModel> response) {
                        Intent intent = new Intent();
                        intent.setClass(MeetingList.this,Meeting.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("meetingId",checkBtn);
                        bundle.putString("memberEmail",email);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        //Toast.makeText(MeetingList.this,"success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CheckinModel> call, Throwable t) {
                        Toast.makeText(MeetingList.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Intent intent = new Intent();
                intent.setClass(MeetingList.this,Meeting.class);
                Bundle bundle = new Bundle();
                bundle.putInt("meetingId",checkBtn);
                bundle.putString("memberEmail",email);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    };

    //signout action
    private Button.OnClickListener signoutListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            CheckinModel checkin = new CheckinModel();
            checkin.setMemberEmail(email);
            checkin.setMeetingId(checkBtn);
            checkin.setMeetingroomId(roomId);
            SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
            for(int i=0;i<meetingId.size();i++){
                if(meetingId.get(i)==checkBtn){
                    login_time = logintime.get(i);
                }
            }
            checkin.setLoginTime(login_time);
            checkin.setLogoutTime(sdf3.format(new java.util.Date()));
            if(checkBtn==-1){
                Intent intent = new Intent();
                intent.setClass(MeetingList.this,MainActivity.class);
                startActivity(intent);
                MeetingList.this.finish();
            }else{
                String id = String.valueOf(checkBtn);
                ServiceFactory.getCheckinApi().postCheckin(email,id,checkin).enqueue(new Callback<CheckinModel>() {
                    @Override
                    public void onResponse(Call<CheckinModel> call, Response<CheckinModel> response) {
                        Intent intent = new Intent();
                        intent.setClass(MeetingList.this,MainActivity.class);
                        startActivity(intent);
                        MeetingList.this.finish();
                    }

                    @Override
                    public void onFailure(Call<CheckinModel> call, Throwable t) {

                    }
                });
            }

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
                return false;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
                //do something
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
                //这里操作是没有返回结果的
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReciever);
        timer.cancel();
        timer = null;
    }

}
