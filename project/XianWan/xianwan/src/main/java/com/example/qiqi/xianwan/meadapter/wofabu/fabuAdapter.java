package com.example.qiqi.xianwan.meadapter.wofabu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Commodity;

import java.util.List;

public class fabuAdapter extends BaseAdapter {

    private List<Commodity> commodities;
    private int itemLayoutId;
    private Context context;

    public fabuAdapter(Context context, List<Commodity> commodities, int itemLayoutId){
        this.context = context;
        this.commodities = commodities;
        this.itemLayoutId = itemLayoutId;

    }

    @Override
    public int getCount() {
        if(null != commodities){
            return commodities.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(null != commodities){
            return commodities.get(position);
        }else{
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(null == convertView) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId, null);
        }
        ImageView imageView = convertView.findViewById(R.id.fabu_img);
       TextView introduce = convertView.findViewById(R.id.fabu_introduce);
       TextView textView=convertView.findViewById(R.id.tv_price);

Commodity commodity=commodities.get(position);
        Glide.with(context).load(commodity.getImage()).into(imageView);
       introduce.setText(commodity.getIntroduce());
       textView.setText(commodity.getPrice());
        return convertView;
    }

}
