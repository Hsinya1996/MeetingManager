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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.utaipei.meetingmanager.http.Model.MeetingroomModel;
import com.example.utaipei.meetingmanager.http.Model.SeatModel;
import com.example.utaipei.meetingmanager.http.ServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cindy on 2017/11/19.
 */

public class RoomSeats extends AppCompatActivity {
    private EditText idX,idY;
    private Button setUp;
    private WifiManager wifiManager;
    private List<ScanResult> wifiList;
    private WifiScanReceiver wifiReciever;
    private ArrayList<String> roomIds = new ArrayList<String>();
    private Spinner spinner;
    private String room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_seats);

        // 初始化控件
        spinner = (Spinner) findViewById(R.id.spinner);
        roomIds.add("");
        // 建立数据源
        ServiceFactory.getMeetingroomApi().getCall().enqueue(new Callback<List<MeetingroomModel>>() {
            @Override
            public void onResponse(Call<List<MeetingroomModel>> call, Response<List<MeetingroomModel>> response) {
                int n = response.body().size();
                for(int i=0;i<n;i++){
                    roomIds.add(response.body().get(i).getRoomId());
                }
                createSpinner();
            }

            @Override
            public void onFailure(Call<List<MeetingroomModel>> call, Throwable t) {

            }
        });

        idX = (EditText)findViewById(R.id.x);
        idY = (EditText)findViewById(R.id.y);

        setUp = (Button)findViewById(R.id.setUp);

        //開啟wifi
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()){
            setUp.setOnClickListener(setUpListener);
        }
    }

    //add data of roomIds to spinner item
    public void createSpinner(){
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                room = roomIds.get(pos);
                //Toast.makeText(RoomSeats.this, room, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    //scan wifi lists
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        updateWifi();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    private void updateWifi() throws IOException {
        //post wifi data
        String text = String.valueOf(wifiList.size());
        //Toast.makeText(RoomSeats.this,text,Toast.LENGTH_SHORT).show();

        for(int i=0;i<wifiList.size();i++){
            SeatModel seatModel = new SeatModel();
            seatModel.setRoomId(room);
            int x = Integer.valueOf(idX.getText().toString());
            int y = Integer.valueOf(idY.getText().toString());
            seatModel.setSeatXid(x);
            seatModel.setSeatYid(y);
            seatModel.setSeatSsid(wifiList.get(i).SSID);
            seatModel.setWifiLevel(wifiList.get(i).level);
            seatModel.setMacAddress(wifiList.get(i).BSSID);
            String mac = wifiList.get(i).BSSID;
            String xs = String.valueOf(x);
            String ys = String.valueOf(y);

            ServiceFactory.getSeatApi().postSeats(room,mac,xs,ys,seatModel).enqueue(new Callback<SeatModel>() {
                @Override
                public void onResponse(Call<SeatModel> call, Response<SeatModel> response) {

                }

                @Override
                public void onFailure(Call<SeatModel> call, Throwable t) {

                }
            });

        }
        Toast.makeText(RoomSeats.this,"建立成功!",Toast.LENGTH_SHORT).show();
    }

    //setUp action
    private Button.OnClickListener setUpListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(room.equals("")){
                Toast.makeText(RoomSeats.this,"請輸入會議室名稱",Toast.LENGTH_SHORT).show();
            }else if(idX.getText().toString().isEmpty()){
                Toast.makeText(RoomSeats.this,"請輸入X座標",Toast.LENGTH_SHORT).show();
            }else if(idY.getText().toString().isEmpty()){
                Toast.makeText(RoomSeats.this,"請輸入Y座標",Toast.LENGTH_SHORT).show();
            }else{
                wifiReciever = new WifiScanReceiver();
                registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                //while(wifiList==null){
                    wifiManager.startScan();
                    SystemClock.sleep(800);
                //}
                if(wifiList!=null){
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }else{
                    Toast.makeText(RoomSeats.this,"掃描中,請再按一次按鈕",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    // wifi receiver
    private final class WifiScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context c, Intent intent) {
            wifiList = wifiManager.getScanResults();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
