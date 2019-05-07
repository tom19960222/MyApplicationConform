package com.example.myapplicationconform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class EventWebView extends AppCompatActivity {

    private WebView web_view;
    private String url = "http://www.csie.tku.edu.tw/main.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_web_view);


        web_view = (WebView) findViewById(R.id.WebView);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.loadUrl(url);

    }
    public void onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack();
            return;
        }
        super.onBackPressed();
    }
}
