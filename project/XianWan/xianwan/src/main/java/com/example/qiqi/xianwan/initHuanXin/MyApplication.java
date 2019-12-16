package com.example.qiqi.xianwan.initHuanXin;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Headpic;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyApplication extends Application {
    public static List<Headpic> Headpiclist = new ArrayList<>();
    private static EaseUI easeUI = EaseUI.getInstance();
    private static int q = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        EaseUI.getInstance().init(this,null);

    }
    public EaseUser getUserInfo(String username){
        EaseUser user = null;
        q++;
//          Log.i("hxr","我执行了第"+q+"次,名字是"+username+"数组长为"+list.size()+"111 de url:"+list.get(0).getUrl());
        if(Headpiclist.size()==0){
            Log.i("hxr","wuxiao");
        }else{
            Log.i("hxr","数"+Headpiclist.get(0).getUserAccount());
            for(int i=0;i<Headpiclist.size();i++){
                if(username.equals(Headpiclist.get(i).getUserAccount())){
                    user = new EaseUser(username);
                    user.setAvatar(Headpiclist.get(i).getAddress());
                    return user;
                }
            }
        }
        return null;
    }

    public void setEaseUIProviders(){
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }
}
