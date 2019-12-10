package com.example.qiqi.xianwan.initHuanXin;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        EaseUI.getInstance().init(this,null);
<<<<<<< HEAD
<<<<<<< HEAD
       // EMClient.getInstance().setDebugMode(true);
=======
>>>>>>> 73eb95b971746d83f06c8547b69df6b68f9de50a
=======
>>>>>>> 73eb95b971746d83f06c8547b69df6b68f9de50a
//        opptions.setAcceptInvitationAlways(false);
    }
}
