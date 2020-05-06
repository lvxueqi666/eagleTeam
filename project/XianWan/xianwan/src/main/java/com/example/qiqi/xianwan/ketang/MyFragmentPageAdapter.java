package com.example.qiqi.xianwan.ketang;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {
    //用来存放多个fragment
    private List<Fragment> fragmentList;
    //TabLayout内的标题
    private String[] mTitles = new String[]{"童话故事", "诗词", "科普视频"};

    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
