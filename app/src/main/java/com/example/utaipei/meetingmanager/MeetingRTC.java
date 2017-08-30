package com.example.utaipei.meetingmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View.OnClickListener;

/**
 * Created by cindy on 2017/7/16.
 */

public class MeetingRTC extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.meeting_rtc, container, false);
        Button button = (Button) rootView.findViewById(R.id.rtc);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(getActivity(), ConnectActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
