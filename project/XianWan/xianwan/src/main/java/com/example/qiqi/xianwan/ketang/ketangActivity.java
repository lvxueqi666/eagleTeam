package com.example.qiqi.xianwan.ketang;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;

import java.util.ArrayList;
import java.util.List;

public class ketangActivity extends AppCompatActivity{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentManager fragmentManager;
    private MyFragmentPageAdapter myFragmentPageAdapter;
    private List<Fragment> fragments=new ArrayList<>();
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ketangactivity);

        mTabLayout= (TabLayout) findViewById(R.id.tablayout);
        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        tv=findViewById(R.id.tv_search);
        //跳转到查询页面
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ketangActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        fragmentManager=getSupportFragmentManager();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        myFragmentPageAdapter=new MyFragmentPageAdapter(fragmentManager,fragments);
        mViewPager.setAdapter(myFragmentPageAdapter);

        //将TabLayout和ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);



    }

}
