package com.example.utaipei.meetingmanager;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utaipei.meetingmanager.http.Model.MemberModel;
import com.example.utaipei.meetingmanager.http.Model.OrganizerModel;
import com.example.utaipei.meetingmanager.http.ServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_RECORD_AUDIO = 101;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 103;
    private Button login;
    private WifiManager wifiManager;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 102 ;
    private LocationManager locateManager;
    private TextInputLayout emailInputLayout,pwdInputLayout;
    private EditText email,pwd;
    private int check=0;
    private RadioGroup userRadioGroup;
    private RadioButton generaluser,manager;
    private int user = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //如果沒有授權使用定位就會跳出來這個
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestLocationPermission(); // 詢問使用者開啟權限
        }

        //Camera permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //如果沒有授權使用相機就會跳出來這個
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestCameraPermission(); // 詢問使用者開啟權限
        }

        //Microphone permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //如果沒有授權使用麥克風就會跳出來這個
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestMicrophonePermission(); // 詢問使用者開啟權限
        }

        //WRITE_EXTERNAL_STORAGE permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //如果沒有授權使用儲存空間就會跳出來這個
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestWritePermission(); // 詢問使用者開啟權限
        }

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
        }

        //開啟GPS
        boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled( getContentResolver(), LocationManager.GPS_PROVIDER );
        locateManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(!gpsEnabled){ //if gps is disabled
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("警示");
            dialog.setMessage("你的gps並沒有開啟, 是否開啟?");
            //dialog.setIconAttribute(android.R.attr.alertDialogIcon);
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE,"確認", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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
        }

        emailInputLayout = (TextInputLayout)findViewById(R.id.emailInputLayout);
        email = (EditText)findViewById(R.id.email);
        //modify keyboard "Enter" feature
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        pwdInputLayout = (TextInputLayout)findViewById(R.id.pwdInputLayout);
        pwd = (EditText)findViewById(R.id.pwd);
        pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });

        userRadioGroup = (RadioGroup)findViewById(R.id.userRadioGroup);
        generaluser = (RadioButton)findViewById(R.id.user);
        manager = (RadioButton)findViewById(R.id.manager);
        userRadioGroup.setOnCheckedChangeListener(userRadioGroupListener);

        login = (Button)findViewById(R.id.signIn);
        login.setOnClickListener(loginListener);
    }

    //request location permission
    private void requestLocationPermission() {
        // 如果裝置版本是6.0（包含）以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission = checkSelfPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION);

            // 如果未授權
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                // 請求授權
                //     第一個參數是請求授權的名稱
                //     第二個參數是請求代碼
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_FINE_LOCATION_PERMISSION);
            }
            else {
                // 啟動地圖與定位元件

            }
        }
    }

    //request camera permission
    private void requestCameraPermission() {
        // 如果裝置版本是6.0（包含）以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission = checkSelfPermission(
                    Manifest.permission.CAMERA);

            // 如果未授權
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                // 請求授權
                //     第一個參數是請求授權的名稱
                //     第二個參數是請求代碼
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
            else {
                // 啟動相機

            }
        }
    }

    //request Microphone permission
    private void requestMicrophonePermission() {
        // 如果裝置版本是6.0（包含）以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission = checkSelfPermission(
                    Manifest.permission.RECORD_AUDIO);

            // 如果未授權
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                // 請求授權
                //     第一個參數是請求授權的名稱
                //     第二個參數是請求代碼
                requestPermissions(
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_AUDIO);
            }
            else {
                // 啟動麥克風

            }
        }
    }

    //request WRITE_EXTERNAL_STORAGE permission
    private void requestWritePermission() {
        // 如果裝置版本是6.0（包含）以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission = checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            // 如果未授權
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                // 請求授權
                //     第一個參數是請求授權的名稱
                //     第二個參數是請求代碼
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
            }
            else {
                // 允許使用儲存空間

            }
        }
    }

    //check user or manager
    private RadioGroup.OnCheckedChangeListener userRadioGroupListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group,int checkedId){
            if(checkedId==R.id.manager){
                user = 1;
            }else if(checkedId==R.id.user){
                user = 2;
            }
        }
    };

    //login action
    private Button.OnClickListener loginListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            emailInputLayout.setError("");
            pwdInputLayout.setError("");

            if(user==0){
                emailInputLayout.setError("請選擇登入權限");
            }else{
                if(!email.getText().toString().isEmpty()){
                    //使用Retrofit封装的方法
                    if(user==1){
                        requestOrganizer();
                    }else if(user==2) {
                        requestUser();
                    }
                }else{
                    emailInputLayout.setError("請輸入帳戶email");
                }
            }

        }
    };

    //get member information from server
    public void requestUser() {

        ServiceFactory.getMemberApi().getCall().enqueue(new Callback<List<MemberModel>>(){
            @Override
            public void onResponse(Call<List<MemberModel>> call, Response<List<MemberModel>> response) {
                int num = response.body().size();
                //check email and password correct
                for(int i=0; i<num; i++){
                    String mem = response.body().get(i).getMemberEmail();
                    if(email.getText().toString().equals(mem)){
                        check = 1;
                        String pw = response.body().get(i).getMemberPassword();
                        if(pwd.getText().toString().equals(pw)){
                            check = 2;
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,MeetingList.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("email",response.body().get(i).getMemberEmail());
                            bundle.putString("name",response.body().get(i).getMemberName());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                }
                if(check==0){
                    emailInputLayout.setError("帳戶未註冊");
                }else if(check==1){
                    pwdInputLayout.setError("密碼輸入錯誤");
                }

                //Toast.makeText(MainActivity.this,"connect server",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {

            }
        });

    }

    //get organizer information from server
    public void requestOrganizer() {
        ServiceFactory.getOrganizerApi().getCall().enqueue(new Callback<List<OrganizerModel>>() {
            @Override
            public void onResponse(Call<List<OrganizerModel>> call, Response<List<OrganizerModel>> response) {
                int num = response.body().size();
                //check email and password correct
                for(int i=0; i<num; i++){
                    String mem = response.body().get(i).getOrganizerEmail();
                    if(email.getText().toString().equals(mem)){
                        check = 1;
                        String pw = response.body().get(i).getOrganizerPassword();
                        if(pwd.getText().toString().equals(pw)){
                            check = 2;
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,RoomSeats.class);
                            startActivity(intent);
                        }
                    }
                }
                if(check==0){
                    emailInputLayout.setError("帳戶未註冊");
                }else if(check==1){
                    pwdInputLayout.setError("密碼輸入錯誤");
                }
            }

            @Override
            public void onFailure(Call<List<OrganizerModel>> call, Throwable t) {

            }
        });
    }

}
