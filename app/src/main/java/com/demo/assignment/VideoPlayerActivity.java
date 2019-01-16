package com.demo.assignment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.demo.assignment.adapters.ViewPagerAdapter;
import com.demo.assignment.models.PlayerInfoModel;
import com.demo.assignment.retrofit.RequestInterface;
import com.demo.assignment.retrofit.RequestListener;
import com.demo.assignment.retrofit.RetrofitAPIRequest;

import retrofit2.Call;

public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private String videoUrl = "";
    private VideoView videoView;
    private int stopPosition;
    private ProgressBar pProgressBar;
    private LinearLayout playersInfoLayout;


    private ImageView infoImageView, closeImageView;

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            stopPosition = savedInstanceState.getInt("position");
        }

        setContentView(R.layout.activity_video_player);

        pProgressBar = findViewById(R.id.progressBar);
        videoView = findViewById(R.id.videoView);
        infoImageView = findViewById(R.id.playerInfoImageView);
        closeImageView = findViewById(R.id.closeLayoutImageView);
        playersInfoLayout = findViewById(R.id.playersLayout);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);

        if (getIntent().getExtras() != null) {
            videoUrl = getIntent().getExtras().getString("video_url");
        }

        infoImageView.setOnClickListener(this);
        closeImageView.setOnClickListener(this);

        //playersInfoLayout.setMinimumWidth(getDisplayDimensions().heightPixels / 3);

        ViewGroup.LayoutParams layoutParams = playersInfoLayout.getLayoutParams();
        layoutParams.width = (int) (getDisplayDimensions().widthPixels * 0.3);
        playersInfoLayout.setLayoutParams(layoutParams);

        startStreamingVideo();

        // Getting Players Information From Server
        getPlayerDataFromServer();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.playerInfoImageView:
                slideToLeft(playersInfoLayout);
                infoImageView.setVisibility(View.GONE);
                break;
            case R.id.closeLayoutImageView:
                slideToRight(playersInfoLayout);
                infoImageView.setVisibility(View.VISIBLE);
                break;
        }
    }

    // To animate view slide out from left to right
    public void slideToRight(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, view.getWidth(), 0, 0);
        animate.setDuration(300);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // To animate view slide out from right to left
    public void slideToLeft(View view) {
        TranslateAnimation animate = new TranslateAnimation(view.getWidth(), 0, 0, 0);
        animate.setDuration(300);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

    private void getPlayerDataFromServer() {
        pProgressBar.setVisibility(View.VISIBLE);
        RequestInterface requestInterface = RetrofitAPIRequest.getRetrofit().create(RequestInterface.class);
        Call<PlayerInfoModel> call = requestInterface.getPlayerList();

        RetrofitAPIRequest.getInstance().doRequest(call, new RequestListener<PlayerInfoModel>() {
            @Override
            public void onResponse(final PlayerInfoModel response) {
                pProgressBar.setVisibility(View.GONE);
                if (response != null) {
                    setUpViewPager(response);

                } else {
                    Toast.makeText(VideoPlayerActivity.this, "Something went wrong while getting player list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                pProgressBar.setVisibility(View.GONE);
                Toast.makeText(VideoPlayerActivity.this, "Something went wrong while getting player list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpViewPager(PlayerInfoModel playerInfoModel) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), playerInfoModel);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorGrey), ContextCompat.getColor(this, R.color.colorPrimaryDark));
        tabLayout.setupWithViewPager(viewPager);

        infoImageView.setVisibility(View.VISIBLE);
    }

    private DisplayMetrics getDisplayDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private void startStreamingVideo() {
        try {
            MediaController mediacontroller = new MediaController(VideoPlayerActivity.this);
            mediacontroller.setAnchorView(videoView);
            Uri video = Uri.parse(videoUrl);
            videoView.setMediaController(mediacontroller);
            videoView.setVideoURI(video);

            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    pProgressBar.setVisibility(View.GONE);
                    videoView.start();
                }
            });
        } catch (Exception e) {
            pProgressBar.setVisibility(View.GONE);
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        stopPosition = videoView.getCurrentPosition();
        videoView.pause();
        outState.putInt("position", stopPosition);
    }

   /* @Override
    protected void onPause() {
        super.onPause();
        stopPosition = videoView.getCurrentPosition();
        videoView.pause();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        videoView.seekTo(stopPosition);
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

