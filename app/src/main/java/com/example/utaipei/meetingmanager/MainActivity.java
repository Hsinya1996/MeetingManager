package com.example.utaipei.meetingmanager;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
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
    private EditText email,pwd;
    private TextView ch;

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

        email = (EditText)findViewById(R.id.email);
        pwd = (EditText)findViewById(R.id.pwd);

        login = (Button)findViewById(R.id.signIn);
        login.setOnClickListener(loginListener);
    }

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
            ch = (TextView)findViewById(R.id.ch);
            //使用Retrofit封装的方法
            request();

//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this,MeetingList.class);
//            startActivity(intent);
        }
    };


    public void request() {


//        //创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://163.21.245.147:443/") // 设置 网络请求 Url
//                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
//                .build();
//        MemberApi request = retrofit.create(MemberApi.class);
        //对 发送请求 进行封装

        ServiceFactory.getMemberApi().getCall().enqueue(new Callback<List<MemberModel>>(){
            @Override
            public void onResponse(Call<List<MemberModel>> call, Response<List<MemberModel>> response) {
                String text = String.valueOf(response.body().size());
                String text2 = response.body().get(0).getMemberEmail();
                ch.setText(text);
                Toast.makeText(MainActivity.this,"connect server",Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {

            }
        });





       // if(call.isExecuted()==false){ ch.setText("fai");}
        //ch.setText("3");
//        call.enqueue(new Callback<MemberModel>() {
//            //请求成功时回调
//            @Override
//            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
//                // 处理返回的数据结果
//                response.body().getMemberEmail();
//                response.body().getMemberPassword();
//
//                ch.setText("5");
//            }
//
//            //请求失败时回调
//            @Override
//            public void onFailure(Call<MemberModel> call, Throwable throwable) {
//                //System.out.println("FAIL");
//                ch.setText("fail");
//            }
//        });
    }

}
