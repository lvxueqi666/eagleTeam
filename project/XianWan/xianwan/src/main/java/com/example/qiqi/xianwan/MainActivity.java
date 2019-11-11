package com.example.qiqi.xianwan;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.qiqi.xianwan.fragment.HomeFragment;
import com.example.qiqi.xianwan.fragment.MeFragment;
import com.example.qiqi.xianwan.fragment.MessageFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
}
