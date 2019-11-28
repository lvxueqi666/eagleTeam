package com.example.qiqi.xianwan.womaidao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.qiqi.xianwan.R;

public class womaidao extends AppCompatActivity
{
    private Button maidao_back;
    private CustomeClickListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.me_womaidao);
        getViews();
        regListener();

    }

    private void getViews() {
        maidao_back = findViewById(R.id.maidao_back);
    }


    private void regListener() {
        {
            listener = new CustomeClickListener();
            maidao_back.setOnClickListener(listener);
        }


    }

    private class CustomeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.maidao_back:
                    finish();
                    break;
            }
        }
    }
}