package com.example.qiqi.xianwan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;  //延迟3秒
    private Handler handler;
    String time = "3";
    private TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                SplashActivity.this.startActivity(intent);
//                SplashActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGHT);
        skip = findViewById(R.id.skip);

        handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                skip.setText(String.valueOf(msg.what) + "s|跳过");
            }
        };



        new Thread(){
            @Override
            public void run() {
                if (time != null && !time.equals("")) {
                    for (int i = Integer.valueOf(time); i >= 0; i--){
                        handler.sendEmptyMessage(i);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
            }
        }.start();

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = "";//为了避免点击跳过之后，普通页的线程未结束
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        });



    }
}
