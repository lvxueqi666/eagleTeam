package com.example.qiqi.xianwan.zhishidabipin.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.zhishidabipin.bean.QuestionOptionBean;

import java.util.List;

public class OptionsListAdapter extends BaseAdapter {
    private Context mContext;
    ListView lv ;
    int index;
    public List<QuestionOptionBean> options ;
    public OptionsListAdapter(Context context, List<QuestionOptionBean> options,ListView lv,int index) {
        this.mContext = context;
        this.options = options;
        this.lv = lv;
    }


    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.list_item_option, null);
        CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.ctv_name);
        TextView option = (TextView) view.findViewById(R.id.tv_option);
        switch (position){
            case 0:
                option.setBackgroundResource(R.drawable.corner_view);
                break;
            case 1:
                option.setBackgroundResource(R.drawable.corner_view1);
                break;
            case 2:
                option.setBackgroundResource(R.drawable.corner_view2);
                break;
            case 3:
                option.setBackgroundResource(R.drawable.corner_view3);
                break;
            case 4:
                option.setBackgroundResource(R.drawable.corner_view4);
                break;
        }
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"fonts/fatfish.TTF");
        //使用字体成卡通体
        option.setTypeface(tf);
        ctv.setTypeface(tf);

        ctv.setText(options.get(position).getName());
        option.setText(options.get(position).getDescription());
        updateBackground(position, ctv);
        return view;
    }

    public void updateBackground(int position, View view) {
        int backgroundId;
        Drawable background;
        if (lv.isItemChecked(position )) {
            switch (position){
                case 0:
                    backgroundId = R.drawable.option_btn_single_checked0;
                    background = mContext.getResources().getDrawable(backgroundId);
                    view.setBackgroundDrawable(background);
                    break;
                case 1:
                    backgroundId = R.drawable.option_btn_single_checked1;
                    background = mContext.getResources().getDrawable(backgroundId);
                    view.setBackgroundDrawable(background);
                    break;
                case 2:
                    backgroundId = R.drawable.option_btn_single_checked2;
                    background = mContext.getResources().getDrawable(backgroundId);
                    view.setBackgroundDrawable(background) ;
                    break;
                case 3:
                    backgroundId = R.drawable.option_btn_single_checked3;
                    background = mContext.getResources().getDrawable(backgroundId);
                    view.setBackgroundDrawable(background);
                    break;
                case 4:
                    backgroundId = R.drawable.option_btn_single_checked4;
                    background = mContext.getResources().getDrawable(backgroundId);
                    view.setBackgroundDrawable(background);
                    break;
            }
        } else {
            backgroundId = R.drawable.option_btn_single_normal;
            background = mContext.getResources().getDrawable(backgroundId);
            view.setBackgroundDrawable(background) ;
        }
    }
}
