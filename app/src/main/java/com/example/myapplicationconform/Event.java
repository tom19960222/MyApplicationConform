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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Event extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private Intent it;
    private ImageView iv;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        navigationView();
        toolbar.setTitle("活動");
        listView();
    }



    void navigationView() {
        // 連結元件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        iv = (ImageView) findViewById(R.id.toolbar_logo);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //宣告要作的行為
                it = new Intent(Event.this, Login.class);
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
//                    Toast.makeText(Event.this, "首頁", Toast.LENGTH_SHORT).show();
                    it = new Intent(Event.this, MainActivity.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_map) {
                    // 按下「地圖」要做的事
//                    Toast.makeText(Event.this, "地圖", Toast.LENGTH_SHORT).show();
                    it = new Intent(Event.this, MainActivity2.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_feedback) {
//                    Toast.makeText(Event.this, "意見", Toast.LENGTH_SHORT).show();
                    it = new Intent(Event.this, FeedbackActivity.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_event) {
//                    Toast.makeText(Event.this, "活動", Toast.LENGTH_SHORT).show();
                    it = new Intent(Event.this, Event.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_settings) {
                    // 按下「設定」要做的事
//                    Toast.makeText(Event.this, "設定", Toast.LENGTH_SHORT).show();
                    it = new Intent(Event.this, Setting.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_about) {
                    // 按下「關於」要做的事
//                    Toast.makeText(Event.this, "關於", Toast.LENGTH_SHORT).show();
                    it = new Intent(Event.this, About.class);
                    startActivity(it);
                    return true;
                }

                return false;
            }
        });
    }

    void listView() {
        listView = (ListView) findViewById(R.id.listView);
        // 假資料
        // Event Adapter
        EventAdapter adapter = new EventAdapter(Event.this);
//        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
                ListView listView = (ListView) parent;
                Toast.makeText(Event.this, "ID：" + id + "   選單文字："+ listView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

            }
        }); //有問題，連點都不能點
    }
}
