package com.example.myapplicationconform;

import android.app.Application;
import android.media.Image;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// 全域變數
public class GlobalVariable extends Application {
    private String name;     // 後續要做持續登入（不登出再用）
    private int Uid;
    private Image icon;
    private String url = "http://2ccf6ed2.ngrok.io/app/";

    private Retrofit retrofit;

    private MyAPIService api;

    //修改 變數値
    public void setUname(String name){
        this.name = name;
    }
    public void setUid(int Uid){

        this.Uid = Uid;
    }

    //取得 變數值
    public String getUname() {
        return name;
    }
    public int getUid(){
        return Uid;
    }

//    public void setUrl(String url) {
//        this.url = url + "/app/";
//    }

    public MyAPIService getApi() {

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MyAPIService.class);

        return api;
    }
}

// global use
//gv = (GlobalVariable)getApplicationContext();
//int i = gv.getUid();
//Toast.makeText(FeedbackActivity.this, i + "gv", Toast.LENGTH_LONG );