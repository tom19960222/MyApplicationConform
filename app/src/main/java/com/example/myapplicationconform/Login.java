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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private Intent it;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private ImageView iv;
    private EditText name;
    private EditText password;
    private Button login;
    private Button register;
    private GlobalVariable gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        navigationView();
        toolbar.setTitle("登入");
        btn();
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
                it = new Intent(Login.this, Register.class);
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
                    it = new Intent(Login.this, MainActivity.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_map) {
                    // 按下「地圖」要做的事
                    it = new Intent(Login.this, MainActivity2.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_feedback) {
                    it = new Intent(Login.this, FeedbackActivity.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_event) {
                    it = new Intent(Login.this, Event.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_settings) {
                    // 按下「設定」要做的事
                    it = new Intent(Login.this, Setting.class);
                    startActivity(it);
                    return true;
                } else if (id == R.id.action_about) {
                    // 按下「關於」要做的事
                    it = new Intent(Login.this, About.class);
                    startActivity(it);
                    return true;
                }

                return false;
            }
        });
    }

    void btn() {
        login = (Button) findViewById(R.id.button);
        register = (Button) findViewById(R.id.button2);

        register.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                it = new Intent(Login.this, Register.class);
                startActivity(it);
            }
        });

        login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 還是有一點問題，懷疑是因為HTTP status code不一（？
                // 目前 response.body() => null
                gv = (GlobalVariable) getApplicationContext();
                name = (EditText)findViewById(R.id.editText2);
                password = (EditText)findViewById(R.id.editText);
                if (name.getText().toString().matches("")){
                    Toast.makeText(Login.this, "帳號不得為空",Toast.LENGTH_LONG).show();
                }else if(password.getText().toString().matches("")){
                    Toast.makeText(Login.this, "密碼不得為空",Toast.LENGTH_LONG).show();
                } else{

                    Call<loginSchema> call = gv.getApi().login(gv.getUid(), name.getText().toString(), password.getText().toString());
                    call.enqueue(new Callback<loginSchema>() {
                        @Override
                        public void onResponse(Call<loginSchema> call, Response<loginSchema> response) {
                            if(response.code() == 401){
                                Toast.makeText(Login.this, "登入失敗", Toast.LENGTH_LONG).show();
                            }else if(response.code() == 403) {
                                Toast.makeText(Login.this, "尚未註冊", Toast.LENGTH_LONG).show();
                            }else if(response.code() == 200) {
                                Toast.makeText(Login.this, "登入成功", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(Login.this, "伺服器未連線", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<loginSchema> call, Throwable t) {
                            Toast.makeText(Login.this,"Login fail: "+t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });
    }
}
