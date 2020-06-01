package com.example.qiqi.xianwan.zhishidabipin.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.qiqi.xianwan.zhishidabipin.activity.SelectActivity;
import com.example.qiqi.xianwan.zhishidabipin.fragment.QuestionItemFragment;
import com.example.qiqi.xianwan.zhishidabipin.fragment.ScantronItemFragment;

public class ItemAdapter extends FragmentStatePagerAdapter {
    Context context;
    public ItemAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == SelectActivity.questionlist.size()){
            return new ScantronItemFragment(context);
        }
        return new QuestionItemFragment(position, context);
    }

    @Override
    public int getCount() {
        return SelectActivity.questionlist.size()+1;
    }
}
