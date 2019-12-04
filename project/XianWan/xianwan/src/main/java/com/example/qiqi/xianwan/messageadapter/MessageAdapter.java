package com.example.qiqi.xianwan.messageadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Message;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private int resourceId;

    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.userImg = view.findViewById(R.id.iv_userImg);
            viewHolder.productImg = view.findViewById(R.id.iv_productImg);
            viewHolder.userName = view.findViewById(R.id.tv_name);
            viewHolder.rulers = view.findViewById(R.id.tv_rulers);
            viewHolder.date = view.findViewById(R.id.tv_time);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.userImg.setImageResource(message.getUserImgId());
        viewHolder.productImg.setImageResource(message.getProductImgId());
        viewHolder.userName.setText(message.getUserName());
        viewHolder.rulers.setText(message.getRules());
        viewHolder.date.setText(message.getDate());
        return view;
    }

    private class ViewHolder{
        ImageView userImg;
        ImageView productImg;
        TextView userName;
        TextView rulers;
        TextView date;
    }
}

