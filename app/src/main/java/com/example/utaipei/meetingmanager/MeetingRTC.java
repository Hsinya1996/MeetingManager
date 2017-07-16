package com.example.utaipei.meetingmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cindy on 2017/7/16.
 */

public class MeetingRTC extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.meeting_rtc, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.tv);
        textView.setText("rtc");
        return rootView;
    }
}
