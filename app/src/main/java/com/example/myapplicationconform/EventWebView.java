package com.example.myapplicationconform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class EventWebView extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_web_view);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");

        webView(url);


    }



    void webView(String url){
        webview = (WebView) findViewById(R.id.WebView);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 設定可以支援縮放
        webSettings.setSupportZoom(true);
        // 設定出現縮放工具
        webSettings.setBuiltInZoomControls(true);
        //擴大比例的縮放
        webSettings.setUseWideViewPort(true);
        //自適應螢幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.loadUrl(url);

    }
}

