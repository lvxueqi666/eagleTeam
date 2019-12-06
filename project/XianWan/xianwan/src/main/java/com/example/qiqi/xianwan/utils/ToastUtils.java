package com.example.qiqi.xianwan.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast mToast;
    public static void showToast(Context context,int resId,int duration){
        showToast(context,resId,duration);
    }

    public static void showToast(Context context,String msg,int duration){
        if(mToast == null){
            mToast = Toast.makeText(context,msg,duration);
        }else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
