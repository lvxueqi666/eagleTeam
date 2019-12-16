package com.example.qiqi.xianwan.meadapter.wofabu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.meadapter.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BigPic_fabu extends AppCompatActivity {
    private LinearLayout LL_BigPiczay;
    private Button btn_deleteBigPiczay;
    private ImageView deletedBtn;
    private Drawable pic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bigpic_fabu);
        EventBus.getDefault().register(BigPic_fabu.this);
        Log.i("zay123","1");
        btn_deleteBigPiczay = findViewById(R.id.btn_deleteBigPiczay);
        LL_BigPiczay = findViewById(R.id.LL_BigPiczay);
        LL_BigPiczay.setBackground(pic);
        btn_deleteBigPiczay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageEvent messageEvent = new MessageEvent("delete",deletedBtn);
                EventBus.getDefault().postSticky(messageEvent);
                finish();
            }
        });



    }
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onMoonEvent(MessageEvent objEvent) {
        deletedBtn = objEvent.getButton();
        pic = objEvent.getDrawable();

    }

}
