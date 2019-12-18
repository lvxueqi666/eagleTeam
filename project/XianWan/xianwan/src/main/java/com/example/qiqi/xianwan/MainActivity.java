package com.example.qiqi.xianwan;

import android.content.res.Resources;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.qiqi.xianwan.entity.Headpic;
import com.example.qiqi.xianwan.fragment.HomeFragment;
import com.example.qiqi.xianwan.fragment.MeFragment;
import com.example.qiqi.xianwan.fragment.MessageFragment;
import com.example.qiqi.xianwan.initHuanXin.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.qiqi.xianwan.initHuanXin.MyApplication.Headpiclist;

public class MainActivity extends AppCompatActivity {

    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();
    private String hostIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hostIp = getResources().getString(R.string.hostStr);


        headpic();
        MyApplication myApplication = new MyApplication();
        myApplication.setEaseUIProviders();

        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent);

        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
                .setIndicator(getTabSpecView("tag1",R.drawable.home,"首页"));
        fragmentTabHost.addTab(tabSpec1, HomeFragment.class,null);


        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("tag2")
                .setIndicator(getTabSpecView("tag2",R.drawable.message1,"消息"));
        fragmentTabHost.addTab(tabSpec2, MessageFragment.class,null);

        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("tag3")
                .setIndicator(getTabSpecView("tag3",R.drawable.me1,"我的"));
        fragmentTabHost.addTab(tabSpec3,MeFragment.class,null);

        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.drawable.home);


        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.drawable.home);
                        imageViewMap.get("tag2").setImageResource(R.drawable.message1);
                        imageViewMap.get("tag3").setImageResource(R.drawable.me1);
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.drawable.home1);
                        imageViewMap.get("tag2").setImageResource(R.drawable.message);
                        imageViewMap.get("tag3").setImageResource(R.drawable.me1);
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.drawable.home1);
                        imageViewMap.get("tag2").setImageResource(R.drawable.message1);
                        imageViewMap.get("tag3").setImageResource(R.drawable.me);
                        break;
                }
            }
        });
    }


    public View getTabSpecView(String tag, int imageResourceId, String title){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tabspace,null);

        ImageView imageView = view.findViewById(R.id.id_icon);
        TextView textView = view.findViewById(R.id.iv_title);
        imageView.setImageResource(imageResourceId);
        textView.setText(title);
        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);
        return view;

    }
    public void headpic(){
        new Thread(){
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://"+ hostIp +":8080/XianWanService/Android4Headpic")
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Headpiclist.clear();
                    Response response = call.execute();
                    String message = response.body().string();
                    Log.i("aaa","a"+message);
                    if(message != null){
                        JSONArray jsonArray = new JSONArray(message);
                        for(int i = 0; i < jsonArray.length();i++) {
                            String objStr = jsonArray.getString(i);
                            JSONObject jsonObject = new JSONObject(objStr);
                            Headpic pic = new Headpic();
                            pic.setUserAccount(jsonObject.getString("userAccount"));
                            pic.setAddress(jsonObject.getString("address"));
                            Headpiclist.add(pic);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
