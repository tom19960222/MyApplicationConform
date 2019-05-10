package com.example.myapplicationconform;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class product_image extends AppCompatActivity {

    private int count;
    private ArrayList<String> url;
    private GlobalVariable gv;
    private ImageButton iv;
    private TextView t ;
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_image);

        gv = (GlobalVariable)getApplicationContext();

        Bundle bundle = getIntent().getExtras();
        url = bundle.getStringArrayList("url");
//        iv = (ImageButton) findViewById(R.id.imageView6) ;
//        t = (TextView)findViewById(R.id.textView5);
//
//
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for(count = 0; count < url.size(); count++){
//                    Picasso.get().load(gv.getUrl()+url.get(count)).into(iv);
//                    t.setText(String.valueOf(count));
//                    if(count == url.size()) {
//                        count = 0;
//                    }
//                }
//            }
//        });

        banner = (Banner)findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                Picasso.get().load(gv.getUrl()+ path).into(imageView);
            }
        });
        //设置图片集合
        banner.setImages(url);
        //banner设置方法全部调用完毕时最后调用

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        banner.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}

