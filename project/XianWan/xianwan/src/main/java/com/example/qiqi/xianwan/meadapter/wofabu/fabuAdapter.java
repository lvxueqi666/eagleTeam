package com.example.qiqi.xianwan.meadapter.wofabu;

import android.content.Context;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
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
        Button button=convertView.findViewById(R.id.delete_fabu);

Commodity commodity=commodities.get(position);
        Glide.with(context).load(commodity.getImage()).transform(new GlideRoundTransform(context,10)).into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现删除数据
                commodities.remove(position);
                notifyDataSetChanged();
            }
        });


       introduce.setText(commodity.getIntroduce());
       textView.setText(commodity.getPrice());
        return convertView;
    }
}
