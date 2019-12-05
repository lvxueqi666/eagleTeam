package com.example.qiqi.xianwan.meadapter.wofabu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;

import org.greenrobot.eventbus.EventBus;

public class wofabu extends AppCompatActivity
{
    private TextView content_fabu;
    private Button btn_wofabu_back;

    private CustomeClickListener listener;
    private EventBus eventBus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.me_wofabu);


        getViews();
        regListener();


    }

    private void regListener() {
        listener = new CustomeClickListener();
        content_fabu.setOnClickListener(listener);
        btn_wofabu_back.setOnClickListener(listener);

    }

    private void getViews() {
        content_fabu=findViewById(R.id.content_fabu);
        btn_wofabu_back=findViewById(R.id.btn_wofabu_back);

    }

    private class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_wofabu_back:
                    finish();
                    break;

                case R.id.content_fabu:
                    Intent intent3=new Intent();
                    intent3.setClass(wofabu.this,content_fabu.class);
                    startActivity(intent3);

            }
        }
    }
}
