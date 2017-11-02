package com.example.utaipei.meetingmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utaipei.meetingmanager.http.Model.FeedbackModel;
import com.example.utaipei.meetingmanager.http.Model.MeetingModel;
import com.example.utaipei.meetingmanager.http.ServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cindy on 2017/7/16.
 */

public class MeetingFeedback extends Fragment{
    private int meetingId;
    private String email;
    private View rootView;
    private RadioGroup timeRadioGroup,locationRadioGroup,itineraryRadioGroup;
    private RadioButton time_good,time_soso,time_bad,location_good,location_soso,location_bad,itinerary_good,itinerary_soso,itinerary_bad;
    private int time=0,location=0,itinerary=0;
    private EditText suggestion;
    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.meeting_feedback, container, false);

        //get data from Meeting activity
        Bundle bundle = getArguments();
        meetingId = bundle.getInt("meetingId");
        email = bundle.getString("memberEmail");

        //get interface elements' id
        timeRadioGroup = (RadioGroup)rootView.findViewById(R.id.timeRadioGroup);
        time_good = (RadioButton)rootView.findViewById(R.id.time_good);
        time_soso = (RadioButton)rootView.findViewById(R.id.time_soso);
        time_bad = (RadioButton)rootView.findViewById(R.id.time_bad);
        timeRadioGroup.setOnCheckedChangeListener(timeRadioGroupListener);

        locationRadioGroup = (RadioGroup)rootView.findViewById(R.id.locationRadioGroup);
        location_good = (RadioButton)rootView.findViewById(R.id.location_good);
        location_soso = (RadioButton)rootView.findViewById(R.id.location_soso);
        location_bad = (RadioButton)rootView.findViewById(R.id.location_bad);
        locationRadioGroup.setOnCheckedChangeListener(locationRadioGroupListener);

        itineraryRadioGroup = (RadioGroup)rootView.findViewById(R.id.itineraryRadioGroup);
        itinerary_good = (RadioButton)rootView.findViewById(R.id.itinerary_good);
        itinerary_soso = (RadioButton)rootView.findViewById(R.id.itinerary_soso);
        itinerary_bad = (RadioButton)rootView.findViewById(R.id.itinerary_bad);
        itineraryRadioGroup.setOnCheckedChangeListener(itineraryRadioGroupListener);

        suggestion = (EditText)rootView.findViewById(R.id.suggestion);
        submit = (Button)rootView.findViewById(R.id.submit);
        submit.setOnClickListener(submitListener);

        return rootView;
    }

    private RadioGroup.OnCheckedChangeListener timeRadioGroupListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId){
            if(checkedId==R.id.time_good){
                time = 3;
            }else if(checkedId==R.id.time_soso){
                time = 2;
            }else if(checkedId==R.id.time_bad){
                time = 1;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener locationRadioGroupListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId){
            if(checkedId==R.id.location_good){
                location = 3;
            }else if(checkedId==R.id.location_soso){
                location = 2;
            }else if(checkedId==R.id.location_bad){
                location = 1;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener itineraryRadioGroupListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId){
            if(checkedId==R.id.itinerary_good){
                itinerary = 3;
            }else if(checkedId==R.id.itinerary_soso){
                itinerary = 2;
            }else if(checkedId==R.id.itinerary_bad){
                itinerary = 1;
            }
        }
    };

    //submit action
    private Button.OnClickListener submitListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            //post feedback to server
            FeedbackModel feedback = new FeedbackModel();
            feedback.setMeetingId(meetingId);
            feedback.setMemberEmail(email);
            feedback.setTimeFeedback(time);
            feedback.setLocationFeedback(location);
            feedback.setItineraryFeedback(itinerary);
            feedback.setSuggestions(suggestion.getText().toString());
            String id = String.valueOf(meetingId);
            ServiceFactory.getFeedbackApi().postFeedback(email,id,feedback).enqueue(new Callback<FeedbackModel>() {
                @Override
                public void onResponse(Call<FeedbackModel> call, Response<FeedbackModel> response) {
                    submit.setText("已填寫");
                    Toast.makeText(getActivity(),"感謝您撥冗填寫!!",Toast.LENGTH_SHORT).show();
                    submit.setEnabled(false);
                }

                @Override
                public void onFailure(Call<FeedbackModel> call, Throwable t) {

                }
            });

        }
    };

}
