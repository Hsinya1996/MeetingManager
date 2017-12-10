package com.example.utaipei.meetingmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.utaipei.meetingmanager.http.Model.MeetingModel;
import com.example.utaipei.meetingmanager.http.ServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cindy on 2017/7/16.
 */

public class MeetingIntro extends Fragment {
    private int meetingId;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //get data from Meeting activity
        Bundle bundle = getArguments();
        meetingId = bundle.getInt("meetingId");

        //display meeting information
        rootView = inflater.inflate(R.layout.meeting_intro, container, false);
        final TextView name = (TextView) rootView.findViewById(R.id.name);
        final TextView time = (TextView) rootView.findViewById(R.id.time);
        final TextView place = (TextView) rootView.findViewById(R.id.place);
        final TextView speaker = (TextView) rootView.findViewById(R.id.speaker);
        final TextView organizer = (TextView) rootView.findViewById(R.id.organizer);
        final TextView people = (TextView) rootView.findViewById(R.id.people);
        final TextView content = (TextView) rootView.findViewById(R.id.content);


        ServiceFactory.getMeetingApi().getCall().enqueue(new Callback<List<MeetingModel>>() {
            @Override
            public void onResponse(Call<List<MeetingModel>> call, Response<List<MeetingModel>> response) {
                int num = response.body().size();
                for(int i=0;i<num;i++){
                    if(meetingId==response.body().get(i).getMeetingId()){
                        name.setText(response.body().get(i).getMeetingName());
                        String date = response.body().get(i).getMeetingDate();
                        String[] st = response.body().get(i).getMeetingStarttime().split(":");
                        String[] et = response.body().get(i).getMeetingEndtime().split(":");
                        String tt = st[0]+":"+st[1]+"-"+et[0]+":"+et[1];
                        String dateTime = date + "  " + tt;
                        time.setText(dateTime);
                        place.setText(response.body().get(i).getMeetingroomId());
                        speaker.setText(response.body().get(i).getSpeaker());
                        organizer.setText(response.body().get(i).getOrganizer());
                        people.setText(response.body().get(i).getParticipants());
                        content.setText(response.body().get(i).getContent());
                        content.setMovementMethod(ScrollingMovementMethod.getInstance());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<MeetingModel>> call, Throwable t) {

            }
        });
        return rootView;
    }

}
