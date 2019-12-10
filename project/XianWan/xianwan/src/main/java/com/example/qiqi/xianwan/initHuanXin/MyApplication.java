package com.example.qiqi.xianwan.initHuanXin;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        EaseUI.getInstance().init(this,null);
    }
}
