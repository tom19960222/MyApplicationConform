package com.example.myapplicationconform;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class product_vedio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_vedio);

        Toast.makeText(product_vedio.this, "vedio", Toast.LENGTH_LONG);

        VideoView videoView = (VideoView) findViewById(R.id.VedioView);
        MediaController mc = new MediaController(this);
//        mc.setAnchorView(videoView);
        videoView.setMediaController(mc);

        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vedio));

        videoView.requestFocus();
        videoView.start();
    }

}
