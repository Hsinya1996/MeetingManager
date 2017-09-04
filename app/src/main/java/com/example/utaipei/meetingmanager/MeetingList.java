package com.example.utaipei.meetingmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    final String[] titles = new String[]{ "預算審查" , "報表分析", "部門會議","預算審查" , "報表分析", "部門會議","預算審查" , "報表分析", "部門會議","報表分析", "部門會議" };
    final String[] places = new String[]{ "G509" , "C313", "H412" ,"G509" , "C313", "H412" ,"G509" , "C313", "H412","C313", "H412" };
    final String[] times = new String[]{ "9-11" , "13-16", "15-18" ,"9-11" , "13-16", "15-18" ,"9-11" , "13-16", "15-18","13-16", "15-18" };
    int[] a = new int[]{1,0,1,1,0,1,1,1,0,1,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_list);

        //title name
        tv = (TextView)findViewById(R.id.tv);
        SpannableString content = new SpannableString("今日會議");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv.setText(content);

        //meeting list
        Context mContext = MeetingList.this;
        // 取得 LinearLayout 物件
        ll = (LinearLayout) findViewById(R.id.lists);
        // 將feedviews加入到 LinearLayout 中
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //開啟wifi
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled())
        {
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("警示");
            dialog.setMessage("你的Wi-Fi並沒有開啟, 是否開啟?");
            //dialog.setIconAttribute(android.R.attr.alertDialogIcon);
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE,"確認", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    wifiManager.setWifiEnabled(true);
                    if(wifiManager.WIFI_STATE_ENABLED == 3){
                        timer = new Timer();
                        timer.schedule(new scanTask(),0, 20000) ;
                    }
                }
            });
            dialog.show();
            Button btnPositive =
                    dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
            btnPositive.setTextColor(getResources().getColor(R.color.colorAccent));
            btnPositive.setTextSize(16);
            Window window = dialog.getWindow();
            TextView title = (TextView)window.findViewById(R.id.alertTitle);
            title.setTextColor(Color.RED);
        }else{
            timer = new Timer();
            timer.schedule(new scanTask(),0, 20000) ;
        }

        sh = (TextView)findViewById(R.id.show);

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
                    updateLists();
                    break;

                default:
                    break;
            }
        }
    };

    private void updateLists() {
        ll.removeAllViews();
        for( int i=0;i<titles.length ; ++i) {
            view = inflater.inflate(R.layout.lvitem, null, true);
            lname = (TextView)view.findViewById(R.id.name);
            ltime =  (TextView)view.findViewById(R.id.time);
            lplace = (TextView)view.findViewById(R.id.place);
            lcheck = (Button)view.findViewById(R.id.check);
            lname.setText(titles[i]);
            ltime.setText(times[i]);
            lplace.setText(places[i]);
            if(a[i]==0){
                lcheck.setVisibility(View.INVISIBLE);
                a[i]=1;
            }else{
                a[i]=0;
            }
            ll.addView(view);
            lcheck.setOnClickListener(checkinListener);
        }
        String text = String.valueOf(wifiList.size());
        sh.setText(text);
    }

    //time tack of 10's
    private class scanTask extends TimerTask {
        @Override
        public void run() {
            wifiReciever = new WifiScanReceiver();
            registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiManager.startScan();
            SystemClock.sleep(1000);
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
            String text = String.valueOf(wifiList.size());
            String text2 = String.valueOf(wifiList.get(1).level);
            Toast.makeText(MeetingList.this,text,Toast.LENGTH_SHORT).show();
        }
    }


    //check-in action
    private Button.OnClickListener checkinListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MeetingList.this,Meeting.class);
            startActivity(intent);
        }
    };

    //signout action
    private Button.OnClickListener signoutListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MeetingList.this,MainActivity.class);
            startActivity(intent);
            MeetingList.this.finish();
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
