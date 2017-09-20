package com.example.utaipei.meetingmanager;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utaipei.meetingmanager.http.Model.MemberModel;
import com.example.utaipei.meetingmanager.http.ServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 102 ;
    private LocationManager locateManager;
    private TextInputLayout emailInputLayout,pwdInputLayout;
    private EditText email,pwd;
    private int check=0;

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
        pwdInputLayout = (TextInputLayout)findViewById(R.id.pwdInputLayout);
        pwd = (EditText)findViewById(R.id.pwd);

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

    //login action
    private Button.OnClickListener loginListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            emailInputLayout.setError("");
            pwdInputLayout.setError("");

            //使用Retrofit封装的方法
            request();

        }
    };

    //get member information from server
    public void request() {

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

}
