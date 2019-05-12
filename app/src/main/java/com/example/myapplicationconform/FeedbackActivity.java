package com.example.myapplicationconform;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedbackActivity extends AppCompatActivity {

    // navigation
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private Intent it;
    private ImageView iv;

    // Global
    private GlobalVariable gv;

    // float +
    private FloatingActionButton fab;
    private LayoutInflater inflater;

    private String input;

    // adapter (適配器) => Spinner\list view
    private ProductAdapter productAdapter;

    private Spinner spinner;
    private ListView listView;

    private int Pid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        gv = (GlobalVariable)getApplicationContext();
        navigationView();
        toolbar.setTitle("意見");

        // spin => spinner
        spin();
        listView();

        // 漂浮的 +
        floatButton();
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
                it = new Intent(FeedbackActivity.this, Login.class);
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
//                    Toast.makeText(FeedbackActivity.this, "首頁", Toast.LENGTH_SHORT).show();
                    it = new Intent(FeedbackActivity.this, MainActivity.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_map) {
                    // 按下「地圖」要做的事
//                    Toast.makeText(FeedbackActivity.this, "地圖", Toast.LENGTH_SHORT).show();
                    it = new Intent(FeedbackActivity.this, MainActivity2.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_feedback) {
//                    Toast.makeText(FeedbackActivity.this, "意見", Toast.LENGTH_SHORT).show();
                    it = new Intent(FeedbackActivity.this, FeedbackActivity.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_event) {
//                    Toast.makeText(FeedbackActivity.this, "活動", Toast.LENGTH_SHORT).show();
                    it = new Intent(FeedbackActivity.this, Event.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_settings) {
                    // 按下「設定」要做的事
//                    Toast.makeText(FeedbackActivity.this, "設定", Toast.LENGTH_SHORT).show();
                    it = new Intent(FeedbackActivity.this, Setting.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_about) {
                    // 按下「關於」要做的事
//                    Toast.makeText(FeedbackActivity.this, "關於", Toast.LENGTH_SHORT).show();
                    it = new Intent(FeedbackActivity.this, About.class);
                    startActivity(it);
                    return true;
                }

                return false;
            }
        });
    }


    void floatButton() {
        fab = findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inflater = LayoutInflater.from(FeedbackActivity.this);
                final View v = inflater.inflate(R.layout.dialog_feedback, null);

                new AlertDialog.Builder(FeedbackActivity.this)
                    .setTitle("我的意見")//設定視窗標題
                    .setIcon(R.drawable.ic_mode_comment_black_24dp) //設定對話視窗圖示
                    .setView(v)//設定顯示的視窗頁面
                    .setPositiveButton("SUBMIT",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) (v.findViewById(R.id.feedback));

                            Call<feedbackSchema> call = gv.getApi().feedback(gv.getUid(), Pid, input);

                            call.enqueue(new Callback<feedbackSchema>() {
                                @Override
                                public void onResponse(Call<feedbackSchema> call, Response<feedbackSchema> response) {

                                    Toast.makeText(getApplicationContext(), "意見發布成功", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<feedbackSchema> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "此意見已刪除" , Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    })//設定結束的子視窗
                    .setNeutralButton("CANCEL",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(FeedbackActivity.this, "Cancel",Toast.LENGTH_SHORT).show();
                        }

                    })
                    .show();//呈現對話視窗
            }
        });
    }


    void spin() {
        spinner = (Spinner)findViewById(R.id.spinner);
        // Get Product name
        productAdapter = new ProductAdapter(FeedbackActivity.this);

        spinner.setAdapter(productAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                productName o = productAdapter.getItem(position);
                int test = spinner.getSelectedItemPosition();
                TextView t = (TextView)findViewById(R.id.textView4);
                t.setText(String.valueOf(position));

                System.out.println( "你選的是"+ String.valueOf(test));

                Toast.makeText(parent.getContext(), "你選的是" +  spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                productName o = productAdapter.getItem(1);

                TextView t = (TextView)findViewById(R.id.textView4);
                t.setText(o.toString());
                Toast.makeText(FeedbackActivity.this, "你選的是" + o.getPid() + "預設", Toast.LENGTH_SHORT).show();

            }
        });


    }

    void listView() {
//        listView = (ListView) findViewById(R.id.listView);
//        // Get Feedback
//        // Feedback Adapter
//        FeedbackAdapter adapter = new FeedbackAdapter(FeedbackActivity.this);
////        recycler_view.setLayoutManager(new LinearLayoutManager(this));
//        listView.setAdapter(adapter);
    }


}
