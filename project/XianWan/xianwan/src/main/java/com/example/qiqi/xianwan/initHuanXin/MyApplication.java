package com.example.qiqi.xianwan.initHuanXin;

import android.app.Application;
import android.util.Log;

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
    public static EaseUser getUserInfo(String username){
        EaseUser user = null;
        q++;
//          Log.i("hxr","我执行了第"+q+"次,名字是"+username+"数组长为"+list.size()+"111 de url:"+list.get(0).getUrl());
        if(Headpiclist.size()==0){
            Log.i("hxr","wuxiao");
        }else{
            Log.i("hxr","数"+Headpiclist.get(0).getUserId());
        }
        for(int i=0;i<Headpiclist.size();i++){
            if(username.equals(Headpiclist.get(i).getUserId())){
                user = new EaseUser(username);
                user.setAvatar(Headpiclist.get(i).getAddress());
                return user;
            }
        }
        return null;
    }

    public static void setEaseUIProviders(){
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    public static void headpic(){
        new Thread(){
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.7.89.18:8080/XianWanService/Android4Headpic")
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    String message = response.body().string();
                    Log.i("aaa","a"+message);
                    if(message != null){
                        JSONArray jsonArray = new JSONArray(message);
                        for(int i = 0; i < jsonArray.length();i++) {
                            String objStr = jsonArray.getString(i);
                            JSONObject jsonObject = new JSONObject(objStr);
                            Headpic pic = new Headpic();
                            pic.setUserId(jsonObject.getString("userId"));
                            Log.i("bbb",""+pic.getId());
                            pic.setAddress(jsonObject.getString("address"));
                            Headpiclist.add(pic);
                        }
                    }
                    Log.i("picture","头像遍历完毕");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
