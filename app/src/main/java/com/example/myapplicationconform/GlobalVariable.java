package com.example.myapplicationconform;

import android.app.Application;
import android.media.Image;

import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// 全域變數
public class GlobalVariable extends Application {
    private String name;     // 後續要做持續登入（不登出再用）
    private int Uid;
    private Image icon;
    private String dUrl = "e215aeea";
    private String url = "http://" + dUrl +".ngrok.io/";
    private  String URL = url +"app/";
    private List<Integer> numIcon1 =
            Arrays.asList(
                    R.drawable.ic_looks_one_black_24dp,
                    R.drawable.ic_looks_two_black_24dp);
//    private List<Integer> numIcon1 =
//            Arrays.asList(
//                    R.drawable.one,
//                    R.drawable.two,
//                    R.drawable.three,
//                    R.drawable.four,
//                    R.drawable.five,
//                    R.drawable.six,
//                    R.drawable.seven,
//                    R.drawable.eight,
//                    R.drawable.nine,
//                    R.drawable.ten);

    private List<Integer> numIcon2 =
            Arrays.asList(
                    R.drawable.ic_filter_1_black_24dp,
                    R.drawable.ic_filter_2_black_24dp);
//    private List<Integer> numIcon2 =
//            Arrays.asList(
//                    R.drawable.gone,
//                    R.drawable.gtwo,
//                    R.drawable.gthree,
//                    R.drawable.gfour,
//                    R.drawable.gfive,
//                    R.drawable.gsix,
//                    R.drawable.gseven,
//                    R.drawable.geight,
//                    R.drawable.gnine,
//                    R.drawable.gten);

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
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MyAPIService.class);

        return api;
    }

    public Integer getNumIcon1(int position) {
        return numIcon1.get(position);
    }

    public Integer getNumIcon2(int position) {
        return numIcon2.get(position);
    }

    public String getUrl() {
        return url;
    }

    public String getdUrl() {
        return dUrl;
    }
}

// global use
//gv = (GlobalVariable)getApplicationContext();
//int i = gv.getUid();
//Toast.makeText(FeedbackActivity.this, i + "gv", Toast.LENGTH_LONG );