package com.example.utaipei.meetingmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cindy on 2017/7/16.
 */

public class MeetingIntro extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.meeting_intro, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.content);
        textView.setText("rtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgjrtchuheolifhrioejwijform;irjolijflkje;orijfirjflkrjmfojrijrhvioljvim;jrjt;ijvlhlihgj;boj/;rlk;ljkbjkgjkrhljhljrhlulwknvoknb;kmrkmglk");
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        return rootView;
    }
}
