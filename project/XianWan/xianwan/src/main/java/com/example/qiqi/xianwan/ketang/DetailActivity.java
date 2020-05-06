package com.example.qiqi.xianwan.ketang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.example.qiqi.xianwan.R;

public class DetailActivity extends AppCompatActivity {
    private com.dueeeke.videoplayer.player.VideoView videoView;
    private String url;
    private String title;
    private String content;
    private ScrollView content_detail;
    private TextView content_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_ketang);

        videoView = findViewById(R.id.player);
        Intent intent  = getIntent();


        //这里获取视频地址
        url = intent.getStringExtra("videoPath");
        //这里获取视频标题

        title=intent.getStringExtra("introduce");
        content=intent.getStringExtra("content");
        content_detail=findViewById(R.id.content_detail);
        content_text=findViewById(R.id.content_text);
        content_text.setText(content);



        videoView.setUrl(url); //设置视频地址

        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(title, false);
        videoView.setVideoController(controller); //设置控制器
        videoView.startFullScreen();
        videoView.start(); //开始播放，不调用则不自动播放
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.release();
    }


    @Override
    public void onBackPressed() {
        if (!videoView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}

