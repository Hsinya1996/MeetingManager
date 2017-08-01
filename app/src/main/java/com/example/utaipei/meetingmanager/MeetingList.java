package com.example.utaipei.meetingmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cindy on 2017/7/11.
 */

public class MeetingList extends AppCompatActivity{
    private TextView tv,lname,ltime,lplace;
    private Button sign0ut,lcheck;
    //private ListView listView;


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
        LinearLayout ll = (LinearLayout) findViewById(R.id.lists);
        // 將feedviews加入到 LinearLayout 中
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        String[] titles = new String[]{ "預算審查" , "報表分析", "部門會議","預算審查" , "報表分析", "部門會議","預算審查" , "報表分析", "部門會議","報表分析", "部門會議" };
        String[] places = new String[]{ "G509" , "C313", "H412" ,"G509" , "C313", "H412" ,"G509" , "C313", "H412","C313", "H412" };
        String[] times = new String[]{ "9-11" , "13-16", "15-18" ,"9-11" , "13-16", "15-18" ,"9-11" , "13-16", "15-18","13-16", "15-18" };
        int[] a = new int[]{1,0,1,1,0,1,1,1,0,1,0};

        for( int i=0;i<titles.length ; ++i) {
            final View view = inflater.inflate(R.layout.lvitem, null, true);
            lname = (TextView)view.findViewById(R.id.name);
            ltime =  (TextView)view.findViewById(R.id.time);
            lplace = (TextView)view.findViewById(R.id.place);
            lcheck = (Button)view.findViewById(R.id.check);
            lname.setText(titles[i]);
            ltime.setText(times[i]);
            lplace.setText(places[i]);
            if(a[i]==0){
                lcheck.setVisibility(View.INVISIBLE);
            }
            ll.addView(view);
            lcheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                }
            });
            lcheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent intent = new Intent();
                    intent.setClass(MeetingList.this,Meeting.class);
                    startActivity(intent);
                }
            });
        }
        sign0ut = (Button)findViewById(R.id.signout);
        sign0ut.setOnClickListener(signoutListener);
        //ll.addView(view,0);

        /*listView = (ListView) findViewById(R.id.list);
        // 清單陣列
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
        String[] titles = new String[]{ "預算審查" , "報表分析", "部門會議","預算審查" , "報表分析", "部門會議","預算審查" , "報表分析", "部門會議" };
        String[] places = new String[]{ "G509" , "C313", "H412" ,"G509" , "C313", "H412" ,"G509" , "C313", "H412" };

        for( int i=0;i<titles.length ; ++i) {
            HashMap<String,String> item = new HashMap<String,String>();
            item.put("title",titles[i]);
            item.put("place",places[i]);
            data.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[] {"title", "place"},
                new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);

        //choose the item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MeetingList.this,Meeting.class);
                startActivity(intent);
            }
        });*/

    }

    private Button.OnClickListener signoutListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MeetingList.this,MainActivity.class);
            startActivity(intent);
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
}
