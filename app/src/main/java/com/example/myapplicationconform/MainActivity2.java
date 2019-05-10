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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

    //Beacon
    private static final long DEFAULT_FOREGROUND_SCAN_PERIOD = 1000L;
    private static final long DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD = 1000L;
    public static final String IBEACON_FORMAT = "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v";

    private BeaconManager beaconManager;
    private BluetoothAdapter bA;
    public LocationManager LM;

    private int path;
    private List<Integer> result;
    private String url;

    // 全域變數
    private GlobalVariable gv;

    // 新增元件
    private RelativeLayout relativeLayout;
    private ImageButton I;
    private ImageButton ib;
    private List<Integer> item = new ArrayList<Integer>();

    private productSchema q;
    private ImageView Ico;
    private TextView title;
    private TextView description;
    private Button image;
    private Button vedio;



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
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_FORMAT));
        beaconManager.bind(this);

        if (!LM.isProviderEnabled(LM.GPS_PROVIDER) && !bA.getDefaultAdapter().isEnabled()) {
            Toast.makeText(this, "未開啟 Bluetooth & GPS", Toast.LENGTH_LONG).show();
        }else if(!LM.isProviderEnabled(LM.GPS_PROVIDER) && bA.getDefaultAdapter().isEnabled()){
            Toast.makeText(this, "未開啟 GPS", Toast.LENGTH_LONG).show();
        }else if(LM.isProviderEnabled(LM.GPS_PROVIDER) && !bA.getDefaultAdapter().isEnabled()){
            Toast.makeText(this, "未開啟 Bluetooth", Toast.LENGTH_LONG).show();
        }
        // get node // 跳出Dialog ，辨別身份

        Call<nodeSchema> call = gv.getApi().getnode();
        call.enqueue(new Callback<nodeSchema>() {
            @Override
            public void onResponse(Call<nodeSchema> call, Response<nodeSchema> response) {
                List<nodes> l = response.body().getNode();
                relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
                TextView t = (TextView)findViewById(R.id.textView3);

                t.setText(l.size() + "  1");

                for (int i = 0; i<l.size(); i++) {
                    I = new ImageButton(MainActivity2.this);
                    I.setImageResource(R.drawable.ic_place_black_24dp);
                    I.setBackgroundColor(Color.TRANSPARENT);
                    I.setId(l.get(i).getNid()*10 + l.get(i).getPid());
                    I.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CreateImageButton(v.getId());
                        }
                    });
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100, 100);
                    lp.addRule(RelativeLayout.BELOW, R.id.toolbar);
                    lp.setMargins(i*500,i*500,i*500,i*500);
                    I.setLayoutParams(lp);
                    relativeLayout.addView(I);
                    item.add(I.getId());


                }
            }

            @Override
            public void onFailure(Call<nodeSchema> call, Throwable t) {
                Toast.makeText(MainActivity2.this, t.toString(), Toast.LENGTH_LONG);
                TextView tv = (TextView)findViewById(R.id.textView3);
                tv.setText(t.toString()+ "     1T");
            }
        });

    }

    void CreateImageButton(int id) {

        Ico = (ImageView)findViewById(R.id.imgIcon);
        title = (TextView)findViewById(R.id.title);
        description = (TextView)findViewById(R.id.description);

        image = (Button)findViewById(R.id.button4);
        vedio = (Button) findViewById(R.id.button5);

        Call<productSchema> call = gv.getApi().getProduct(id % 10);

        call.enqueue(new Callback<productSchema>() {
            @Override
            public void onResponse(Call<productSchema> call, Response<productSchema> response) {
                q = response.body();

                Picasso.get().load(gv.getUrl()+q.getIcon()).into(Ico);
                title.setText(q.getPname());
                description.setText(q.getDescription());

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        it = new Intent(MainActivity2.this, product_image.class);

                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("url", (ArrayList<String>) q.getImage());
                        it.putExtras(bundle);

                        startActivity(it);
                    }
                });

                vedio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        it = new Intent(MainActivity2.this, product_vedio.class);

                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("url", (ArrayList<String>) q.getImage());
                        it.putExtras(bundle);

                        startActivity(it);
                    }
                });


            }

            @Override
            public void onFailure(Call<productSchema> call, Throwable t) {

            }
        });


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

        myPath.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if (isChecked) {
                    //CheckBox狀態 : 已勾選

                    retrofit2.Call<pathSchema> call = gv.getApi().getPathUq(gv.getUid());

                    call.enqueue(new Callback<pathSchema>() {
                        @Override
                        public void onResponse(retrofit2.Call<pathSchema> call, Response<pathSchema> response) {
                            result = response.body().getPath();
                            for (int i = 0; i<result.size(); i++) {
                                for(int j=0;j<item.size();j++){
                                    if(result.get(i) == item.get(j)/10){
                                        ib = (ImageButton) findViewById(item.get(j));
                                        ib.setColorFilter(Color.BLUE);
                                    }
                                }
                            }

                        }
                        @Override
                        public void onFailure(Call<pathSchema> call, Throwable t) {
                            Toast.makeText(MainActivity2.this,t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    //CheckBox狀態 : 未勾選
                    for(int j=0;j<item.size();j++){
                        ib = (ImageButton) findViewById(item.get(j));
                        ib.clearColorFilter();
                    }
                }
            }
        });

        suggest.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if (isChecked) {
                    //CheckBox狀態 : 已勾選

                    retrofit2.Call<pathSchema> call = gv.getApi().getSuggest();

                    call.enqueue(new Callback<pathSchema>() {
                        @Override
                        public void onResponse(retrofit2.Call<pathSchema> call, Response<pathSchema> response) {
                            result = response.body().getPath();
                            for (int i = 0; i<result.size(); i++) {
                                for(int j=0;j<item.size();j++){
                                    if(result.get(i) == item.get(j)/10){
                                        ib = (ImageButton) findViewById(item.get(j));
                                        ib.setImageResource(gv.getNumIcon1(i));
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<pathSchema> call, Throwable t) {
                            Toast.makeText(MainActivity2.this,t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });


                } else {
                    //CheckBox狀態 : 未勾選
                    for(int j=0;j<item.size();j++){
                        ib = (ImageButton) findViewById(item.get(j));
                        ib.setImageResource(R.drawable.ic_place_black_24dp);
                    }

                }
            }
        });

        wantted.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //判斷CheckBox是否有勾選，同mCheckBox.isChecked()
                if (isChecked) {
                    //CheckBox狀態 : 已勾選

                    retrofit2.Call<pathSchema> call = gv.getApi().getWantted(gv.getUid());

                    call.enqueue(new Callback<pathSchema>() {
                        @Override
                        public void onResponse(retrofit2.Call<pathSchema> call, Response<pathSchema> response) {
                            pathSchema node = response.body();
                            if(node == null) {
                                AlertDialog.Builder myDlg = new AlertDialog.Builder(MainActivity2.this);
                                myDlg.setMessage("請點擊右下角'設定自定義路線'按鈕，並以自己希望的順序點擊座標記號，最後再次重新勾選（先取消再勾選） ")
                                        .setTitle("未設定'自定義路線'")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
                            }else{
                                result = node.getPath();
                                for (int i = 0; i<result.size(); i++) {
                                    for(int j=0;j<item.size();j++){
                                        if(result.get(i) == item.get(j)/10){
                                            ib = (ImageButton) findViewById(item.get(j));
                                            ib.setImageResource(gv.getNumIcon2(i));
                                        }
                                    }
                                }
                            }

                        }
                        @Override
                        public void onFailure(Call<pathSchema> call, Throwable t) {
                            Toast.makeText(MainActivity2.this,t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });


                } else {
                    //CheckBox狀態 : 未勾選
                    for(int j=0;j<item.size();j++){
                        ib = (ImageButton) findViewById(item.get(j));
                        ib.setImageResource(R.drawable.ic_place_black_24dp);
                    }
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

//        beaconManager.addRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
//                Log.i("IIIIII", "didRangeBeaconsInRegion: 调用了这个方法:" + collection.size());
//                if (collection.size() > 0) {
//                    //符合要求的beacon集合
//                    List<Beacon> beacons = new ArrayList<>();
//                    ArrayList myList = new ArrayList();
//                    for (Beacon beacon : collection) {
//                        beacons.add(beacon);
//                        url = UrlBeaconUrlCompressor.uncompress(beacon.getId1().toByteArray());
//                        String[] array  = url.split("/");
////                        String BASE_URL = array[0] + "//" + array[2] + ".ngrok.io";
//
//                        path = Integer.parseInt(array[3]);
//
////                        url = BASE_URL + "/" + path;
//                        if ( gv.getdUrl() == array[2] ){
//                            if( myList.size() < 2 || myList.get(myList.size()-1) != url){
//                                myList.add(url);
//
//                            }
//                        }
//
//
//                    }
//
//                        retrofit2.Call<pathSchema> call = gv.getApi().getpath(gv.getUid(), path);
//
//                        call.enqueue(new Callback<pathSchema>() {
//                            @Override
//                            public void onResponse(retrofit2.Call<pathSchema> call, Response<pathSchema> response) {
//
//                                result = response.body().getPath();
//                            }
//                            @Override
//                            public void onFailure(Call<pathSchema> call, Throwable t) {
////                                Toast.makeText(MainActivity2.this,t.toString(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//                    }
//
//                }
//
//
//        });
//        try {
////            别忘了启动搜索,不然不会调用didRangeBeaconsInRegion方法
//            beaconManager.startRangingBeaconsInRegion(new Region("all-beacons-region", null, null, null));
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
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

