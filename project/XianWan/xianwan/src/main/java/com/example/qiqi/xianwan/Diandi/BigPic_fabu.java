package com.example.qiqi.xianwan.Diandi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.LinearLayout;


import com.example.qiqi.xianwan.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BigPic_fabu extends AppCompatActivity {
    private LinearLayout LL_BigPiczay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bigpic_fabu);
        EventBus.getDefault().register(BigPic_fabu.this);
        LL_BigPiczay = findViewById(R.id.LL_BigPiczay);
        final Intent intent = getIntent();
        String imagePath = intent.getStringExtra("path");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Drawable drawable = new BitmapDrawable(bitmap);
        LL_BigPiczay.setBackground(drawable);


    }
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onMoonEvent(MessageEvent objEvent) {

    }

}
