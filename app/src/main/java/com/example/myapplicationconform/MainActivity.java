package com.example.myapplicationconform;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.altbeacon.beacon.BeaconManager.DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD;
import static org.altbeacon.beacon.BeaconManager.DEFAULT_FOREGROUND_SCAN_PERIOD;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private Intent it;
    private ImageView iv;

    // 檢驗是否第一次開啟ＡＰＰ（目的：透過ＡＰＩ線上註冊ＩＤ）
    private SharedPreferences mSharedPreferences;
    private int firstOpenApp = 0;
    private static final String DATA = "DATA";

    // 全域
    private GlobalVariable gv;

    // Gson
    Gson gson = new Gson();

    // retrofit
    private int result;

    // Beacon
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
//    private static final long DEFAULT_FOREGROUND_SCAN_PERIOD = 1000L;
//    private static final long DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD = 1000L;
//
//    private BeaconManager beaconManager;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        firstOpen();

        permission();

        navigationView();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 設置要用哪個menu檔做為選單
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    void firstOpen() {
        // 檢驗第一次啟動ＡＰＰ & 在 firstOpenAPP 存 UserID
        mSharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        firstOpenApp = mSharedPreferences.getInt("Open", firstOpenApp);

        gv = (GlobalVariable)getApplicationContext();

        if (firstOpenApp == 0) {
            // POST user

            Call<userSchema> call = gv.getApi().getUser();

            call.enqueue(new Callback<userSchema>() {
                @Override
                public void onResponse(Call<userSchema> call, Response<userSchema> response) {

                    Object res = response.body();
                    String json = gson.toJson(res);
                    String test[] = json.split("Uid:");
                    String test1[] = test[1].split(",");

                    Toast.makeText(MainActivity.this,json+" | "+test1[0], Toast.LENGTH_LONG).show();
//                    result = json.getInt("Uid");
                    // 可以註冊可是回傳值有問題
                }
                @Override
                public void onFailure(Call<userSchema> call, Throwable t) {

                    Toast.makeText(MainActivity.this,t.toString(), Toast.LENGTH_LONG).show();
                }
            });
//            firstOpenApp = result;
//            Toast.makeText(MainActivity.this,json+" ", Toast.LENGTH_LONG).show();
//            mSharedPreferences.edit()
//                    .putInt("Open", firstOpenApp)
//                    .apply();
        }
        // 設定全域變數 Uid
        Toast.makeText(MainActivity.this,firstOpenApp+" ", Toast.LENGTH_LONG).show();
       gv.setUid(firstOpenApp);
    }


    void permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Android M Permission check

            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 未取得權限（有沒有開要另外寫）

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override

                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }

                });

                builder.show();

            }
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
            switch (requestCode) {
                case PERMISSION_REQUEST_COARSE_LOCATION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("DDDDD", "coarse location permission granted");
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Functionality limited");
                        builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                        builder.setPositiveButton(android.R.string.ok, null);
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }

                        });
                        builder.show();
                    }
                    return;
                }
            }
        }



    void navigationView () {
        // 連結元件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        toolbar.setTitle("TKU 資工週");
        iv = (ImageView) findViewById(R.id.toolbar_logo);
        iv.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View view){
                //宣告要作的行為
                it = new Intent(MainActivity.this, Login.class);
                startActivity(it);
            }
        });

        // 將drawerLayout和toolbar整合，會出現「三」按鈕
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 選單點擊事件
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // 點選時收起選單
                drawerLayout.closeDrawer(GravityCompat.START);

                // 取得選項id
                int id = item.getItemId();

                // 依照id判斷點了哪個項目並做相應事件
                if (id == R.id.action_home) {
                    // 按下「首頁」要做的事
//                    Toast.makeText(MainActivity.this, "首頁", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_map) {
                    // 按下「地圖」要做的事
//                    Toast.makeText(MainActivity.this, "地圖", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_feedback) {
//                    Toast.makeText(MainActivity.this, "意見", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity.this, FeedbackActivity.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_event) {
//                    Toast.makeText(MainActivity.this, "活動", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity.this, Event.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_settings) {
                    // 按下「設定」要做的事
//                    Toast.makeText(MainActivity.this, "設定", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity.this, Setting.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_about) {
                    // 按下「關於」要做的事
//                    Toast.makeText(MainActivity.this, "關於", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity.this, About.class);
                    startActivity(it);
                    return true;
                }

                return false;
            }
        });
    }

}
