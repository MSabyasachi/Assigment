package com.demo.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playVideoButton = findViewById(R.id.playButton);
        playVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlayingInFullScreen();
            }
        });
    }

    private void startPlayingInFullScreen() {

        String videoUrl = "http://intigralvod1-vh.akamaihd.net/i/3rdparty/Season2017_2018/10_12_2017_Hilal_fath/Highlights/high_,256,512,768,1200,.mp4.csmil/master.m3u8";

        Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
        intent.putExtra("video_url", videoUrl);
        startActivity(intent);
    }
}
