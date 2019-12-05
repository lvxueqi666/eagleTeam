package com.example.qiqi.xianwan.messageadapter.noticeadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Notice;

import java.util.List;

public class NoticeAdapter extends ArrayAdapter<Notice> {
    private int resourceId;
    public NoticeAdapter(Context context, int resource, List<Notice> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Notice notice = getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.noticeDate = view.findViewById(R.id.tv_noticeDate);
            viewHolder.noticeTitle = view.findViewById(R.id.tv_noticeTitle);
            viewHolder.noticeDetails = view.findViewById(R.id.tv_noticeDetail);
            viewHolder.noticeImg = view.findViewById(R.id.iv_noticeImg);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.noticeDate.setText(notice.getNoticeDate());
        viewHolder.noticeTitle.setText(notice.getNoticeTitle());
        viewHolder.noticeDetails.setText(notice.getNoticeDetails());
        viewHolder.noticeImg.setImageResource(notice.getNoticeImg());
        return view;
    }

    private class ViewHolder{
        ImageView noticeImg;
        TextView noticeDate;
        TextView noticeTitle;
        TextView noticeDetails;
    }

}
