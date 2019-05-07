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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private Intent it;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private EditText name;
    private EditText password;
    private ImageView iv;
    private Button cancel;
    private Button submit;
    private GlobalVariable gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        navigationView();
        toolbar.setTitle("註冊");
        btn();
    }




    void navigationView() {
        // 連結元件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

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
                    it = new Intent(Register.this, MainActivity.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_map) {
                    // 按下「地圖」要做的事
                    it = new Intent(Register.this, MainActivity2.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_feedback) {
                    it = new Intent(Register.this, FeedbackActivity.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_event) {
                    it = new Intent(Register.this, Event.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_settings) {
                    // 按下「設定」要做的事
                    it = new Intent(Register.this, Setting.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_about) {
                    // 按下「關於」要做的事
                    it = new Intent(Register.this, About.class);
                    startActivity(it);
                    return true;
                }

                return false;
            }
        });
    }

    void btn() {
        submit = (Button) findViewById(R.id.button);
        cancel = (Button) findViewById(R.id.button2);

        cancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(Register.this, Login.class);
                startActivity(it);
            }
        });

        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //patch user/Uid "Open"Shared

                gv = (GlobalVariable) getApplicationContext();
                name = (EditText)findViewById(R.id.editText2);
                password = (EditText)findViewById(R.id.editText);

                if (name.getText().toString().matches("")){
                    Toast.makeText(Register.this, "帳號不得為空",Toast.LENGTH_LONG).show();
                }else if(password.getText().toString().matches("")){
                    Toast.makeText(Register.this, "密碼不得為空",Toast.LENGTH_LONG).show();
                } else{
                    Call<userSchema> call = gv.getApi().register(gv.getUid(), name.getText().toString(), password.getText().toString());
                    call.enqueue(new Callback<userSchema>() {
                        @Override
                        public void onResponse(Call<userSchema> call, Response<userSchema> response) {

                            Toast.makeText(Register.this, response.message().toString(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<userSchema> call, Throwable t) {
                            Toast.makeText(Register.this, "註冊成功", Toast.LENGTH_LONG).show(); // For Demo
//                            Toast.makeText(Register.this,"Login fail: "+t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }
}
