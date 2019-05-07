package com.example.myapplicationconform;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class About extends AppCompatActivity {

    private Intent it;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private ImageView iv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        apigettest();
        navigationView();
        toolbar.setTitle("關於");
    }


    void apigettest() {
        tv = (TextView) findViewById(R.id.textView) ;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://458bb5b1.ngrok.io/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyAPIService api = retrofit.create(MyAPIService.class);

        Call<productSchema> call = api.getProduct(1);
        call.enqueue(new Callback<productSchema>() {
            @Override
            public void onResponse(Call<productSchema> call, Response<productSchema> response) {

                String result = response.body().getPid().toString();
                tv.setText(result);

            }
            @Override
            public void onFailure(Call<productSchema> call, Throwable t) {
                tv.setText("Failure" + t);
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
                it = new Intent(About.this, Login.class);
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
//                    Toast.makeText(About.this, "首頁", Toast.LENGTH_SHORT).show();
                    it = new Intent(About.this, MainActivity.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_map) {
                    // 按下「地圖」要做的事
//                    Toast.makeText(About.this, "地圖", Toast.LENGTH_SHORT).show();
                    it = new Intent(About.this, MainActivity2.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_feedback) {
//                    Toast.makeText(About.this, "意見", Toast.LENGTH_SHORT).show();
                    it = new Intent(About.this, FeedbackActivity.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_event) {
//                    Toast.makeText(About.this, "活動", Toast.LENGTH_SHORT).show();
                    it = new Intent(About.this, Event.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_settings) {
                    // 按下「設定」要做的事
//                    Toast.makeText(About.this, "設定", Toast.LENGTH_SHORT).show();
                    it = new Intent(About.this, Setting.class);
                    startActivity(it);
                    return true;
                }
                else if (id == R.id.action_about) {
                    // 按下「關於」要做的事
//                    Toast.makeText(About.this, "關於", Toast.LENGTH_SHORT).show();
                    it = new Intent(About.this, About.class);
                    startActivity(it);
                    return true;
                }

                return false;
            }
        });
    }
}
