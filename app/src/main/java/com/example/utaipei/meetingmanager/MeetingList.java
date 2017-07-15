package com.example.utaipei.meetingmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cindy on 2017/7/11.
 */

public class MeetingList extends AppCompatActivity{
    public TextView tv;
    private ListView listView;


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
        listView = (ListView) findViewById(R.id.list);
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
        });

    }
}
