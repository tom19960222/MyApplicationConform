package com.example.myapplicationconform;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity implements BeaconConsumer {

    private Intent it;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private ImageView iv;
    private CheckBox myPath,suggest,wantted;
    private ImageView imageView5,imageView6,imageView7;

    //Beacon
    private static final long DEFAULT_FOREGROUND_SCAN_PERIOD = 1000L;
    private static final long DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD = 1000L;
    public static final String IBEACON_FORMAT = "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v";

    private BeaconManager beaconManager;
    private BluetoothAdapter bA;
    public LocationManager LM;


    // 全域變數
    private GlobalVariable gv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        LM = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        gv = (GlobalVariable) getApplicationContext();

        init();
        navigationView();
        toolbar.setTitle("地圖");

        onBeaconServiceConnect();
        check();
        btn();
    }

    // 載入點的位置跟展品id＆name
    void init() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.setForegroundBetweenScanPeriod(DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD);
        beaconManager.setForegroundScanPeriod(DEFAULT_FOREGROUND_SCAN_PERIOD);
        beaconManager.bind(this);

        if (LM.isProviderEnabled(LM.GPS_PROVIDER)) {
            //定位已打开的处理
            if (!bA.getDefaultAdapter().isEnabled()) {
                Toast.makeText(this, "未開啟 Bluetooth", Toast.LENGTH_LONG).show();
            }
        }else {
            //定位依然没有打开的处理
            Toast.makeText(this, "未開啟 GPS", Toast.LENGTH_LONG).show();
        }
        // get node


    }

    void navigationView() {
        // 連結元件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        iv = (ImageView) findViewById(R.id.toolbar_logo);
        iv.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View view){
                //宣告要作的行為
                it = new Intent(MainActivity2.this, Login.class);
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
//                    Toast.makeText(MainActivity2.this, "首頁", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_map) {
                    // 按下「地圖」要做的事
//                    Toast.makeText(MainActivity2.this, "地圖", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity2.this, MainActivity2.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_feedback) {
//                    Toast.makeText(MainActivity2.this, "意見", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity2.this, FeedbackActivity.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_event) {
//                    Toast.makeText(MainActivity2.this, "活動", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity2.this, Event.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_settings) {
                    // 按下「設定」要做的事
//                    Toast.makeText(MainActivity2.this, "設定", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity2.this, Setting.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_about) {
                    // 按下「關於」要做的事
//                    Toast.makeText(MainActivity2.this, "關於", Toast.LENGTH_SHORT).show();
                    it = new Intent(MainActivity2.this, About.class);
                    startActivity(it);
                    return true;
                }

                return false;
            }
        });
    }

    void check() {
        myPath = (CheckBox) findViewById(R.id.myPath);
        suggest = (CheckBox) findViewById(R.id.Suggest);
        wantted = (CheckBox) findViewById(R.id.Wantted);

        imageView5 = (ImageView)findViewById(R.id.imageView5);
        imageView6 = (ImageView)findViewById(R.id.imageView6);
        imageView7 = (ImageView)findViewById(R.id.imageView7);

        myPath.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if (isChecked) {
                    //CheckBox狀態 : 已勾選
                    imageView5.setColorFilter(Color.YELLOW);
                    imageView6.setColorFilter(Color.YELLOW);
                } else {
                    //CheckBox狀態 : 未勾選
                    imageView5.setColorFilter(Color.BLACK);
                    imageView6.setColorFilter(Color.BLACK);
                }
            }
        });

        suggest.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if (isChecked) {
                    //CheckBox狀態 : 已勾選
                    imageView5.setImageResource(R.drawable.s1);
                    imageView6.setImageResource(R.drawable.s2);
                    imageView7.setImageResource(R.drawable.s3);

                } else {
                    //CheckBox狀態 : 未勾選
                    imageView5.setImageResource(R.drawable.ic_place_black_24dp);
                    imageView6.setImageResource(R.drawable.ic_place_black_24dp);
                    imageView7.setImageResource(R.drawable.ic_place_black_24dp);
                }
            }
        });

        wantted.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if (isChecked) {
                    //CheckBox狀態 : 已勾選

                } else {
                    //CheckBox狀態 : 未勾選

                }
            }
        });
    }

    void btn() {
        Button button3 = (Button)findViewById(R.id.button3);

        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity2.this);
                final View vi = inflater.inflate(R.layout.dialog_map, null);

                new AlertDialog.Builder(MainActivity2.this)
                        .setTitle("自定義路線")//設定視窗標題
                        .setView(vi)//設定顯示的視窗頁面
                        .setPositiveButton("SUBMIT",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                final ImageButton im1 = (ImageButton)findViewById(R.id.imageButton8);
//                                final ImageButton im2 = (ImageButton)findViewById(R.id.imageButton9);
//                                final ImageButton im3 = (ImageButton)findViewById(R.id.imageButton10);
//
//                                final int[] tapCount = {0};
//
//                                im1.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        tapCount[0]++;
//                                        if(tapCount[0] == 1){
//                                           im1.setImageResource(R.drawable.one);
//                                        }else if(tapCount[0] == 2) {
//                                            im1.setImageResource(R.drawable.two);
//                                        }else {
//                                            im1.setImageResource(R.drawable.three);
//                                        }
//                                    }
//                                });
//
//                                im2.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        tapCount[0]++;
//                                        if (tapCount[0] == 1) {
//                                            im2.setImageResource(R.drawable.one);
//                                        } else if (tapCount[0] == 2) {
//                                            im2.setImageResource(R.drawable.two);
//                                        } else {
//                                            im2.setImageResource(R.drawable.three);
//                                        }
//
//                                    }
//                                });
//
//                                im3.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        tapCount[0]++;
//                                        if(tapCount[0] == 1){
//                                            im3.setImageResource(R.drawable.one);
//                                        }else if(tapCount[0] == 2) {
//                                            im3.setImageResource(R.drawable.two);
//                                        }else {
//                                            im3.setImageResource(R.drawable.three);
//                                        }
//
//                                    }
//                                });
//
                                Toast.makeText(getApplicationContext(), "自定義路線成功" , Toast.LENGTH_LONG).show();
                            }
                        })//設定結束的子視窗
                        .setNeutralButton("CANCEL",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(MainActivity2.this, "Cancel",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        })
                        .show();//呈現對話視窗
            }
        });
    }


    @Override
    public void onBeaconServiceConnect() {

        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                Log.i("IIIIII", "didRangeBeaconsInRegion: 调用了这个方法:" + collection.size());
//                updateTextViewMsg("进入didRangeBeaconsInRegion方法");
                if (collection.size() > 0) {
                    //符合要求的beacon集合
                    List<Beacon> beacons = new ArrayList<>();
                    ArrayList myList = new ArrayList();
                    for (Beacon beacon : collection) {
                        beacons.add(beacon);
                        String url = UrlBeaconUrlCompressor.uncompress(beacon.getId1().toByteArray());
                        String[] array  = url.split("/");
                        String BASE_URL = array[0] + "//" + array[2] + ".ngrok.io";

//                        gv.setUrl(BASE_URL);

                        String path = array[3];

                        url = BASE_URL + "/" + path;
                        if( myList.size() < 2 || myList.get(myList.size()-1) != url){
                            myList.add(url);

                            retrofit2.Call<ResponseBody> call = gv.getApi().getpath(gv.getUid(), Integer.parseInt(path));

                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                                    Object result = response.body();

                                    Toast.makeText(MainActivity2.this,result.toString(), Toast.LENGTH_LONG).show();

                                }
                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    Toast.makeText(MainActivity2.this,t.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }


                }
            }

        });
        try {
//            别忘了启动搜索,不然不会调用didRangeBeaconsInRegion方法
            beaconManager.startRangingBeaconsInRegion(new Region("all-beacons-region", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind( this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

