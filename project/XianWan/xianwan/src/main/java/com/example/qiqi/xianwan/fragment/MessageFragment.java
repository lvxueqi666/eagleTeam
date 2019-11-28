package com.example.qiqi.xianwan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Message;
import com.example.qiqi.xianwan.messageadapter.ActivitiesActivity;
import com.example.qiqi.xianwan.messageadapter.InteractionActivity;
import com.example.qiqi.xianwan.messageadapter.MessageAdapter;
import com.example.qiqi.xianwan.messageadapter.MessageChatActivity;
import com.example.qiqi.xianwan.messageadapter.NoticeActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    private ImageView iv_notice;
    private ImageView iv_interaction;
    private ImageView iv_activities;
    private ListView lv_message;
    private List<Message>  messageList;
    private SmartRefreshLayout srl;
    private MessageAdapter adapter;
    private CustomOnClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment_layout,container,false);

        //注册
        iv_notice = view.findViewById(R.id.iv_notice);
        iv_interaction = view.findViewById(R.id.iv_interaction);
        iv_activities = view.findViewById(R.id.iv_activity);
        lv_message = view.findViewById(R.id.lv_message);
        srl = view.findViewById(R.id.message_srl);

        //注册点击监听
        registerListenrs();

        //数据初始化
        messageList = initData();

        //列表点击事件
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = messageList.get(position);
                Toast.makeText(
                        getActivity(),
                        message.getUserName(),
                        Toast.LENGTH_SHORT
                ).show();
                Intent intent = new Intent(getActivity(), MessageChatActivity.class);
                intent.putExtra("name",message.getUserName());
                startActivity(intent);
            }
        });

        //adapter
        adapter = new MessageAdapter(getActivity(),R.layout.message_listview_item,messageList);
        lv_message.setAdapter(adapter);

        //SmartRefresh
        //设置刷新事件
        srl.setReboundDuration(1000);
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl.finishRefresh();
                Toast.makeText(
                        getContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        return view;
    }

    //下拉
    private void refreshData() {
        messageList.clear();
        messageList.addAll(initData());
        adapter.notifyDataSetChanged();
    }

    //数据插入
    private List<Message> initData() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("李小同","信誉良好","七天前",R.drawable.touxiang,R.drawable.iphone));
        messageList.add(new Message("徐小航","信誉良好","一天前",R.drawable.touxiang,R.drawable.iphone));
        messageList.add(new Message("吕小齐","信誉良好","五天前",R.drawable.touxiang,R.drawable.iphone));
        messageList.add(new Message("霍小瑞","信誉良好","七天前",R.drawable.touxiang,R.drawable.iphone));
        messageList.add(new Message("王小胖","信誉良好","七天前",R.drawable.touxiang,R.drawable.iphone));
        messageList.add(new Message("张小阳","信誉良好","八天前",R.drawable.touxiang,R.drawable.iphone));
        messageList.add(new Message("李小宵","信誉良好","一个月",R.drawable.touxiang,R.drawable.iphone));
        messageList.add(new Message("朱小辰","信誉良好","已失联",R.drawable.touxiang,R.drawable.iphone));
        return messageList;
    }

    //点击监听事件注册
    private void registerListenrs() {
        listener = new CustomOnClickListener();
        iv_notice.setOnClickListener(listener);
        iv_interaction.setOnClickListener(listener);
        iv_activities.setOnClickListener(listener);
    }

    //点击事件
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_notice:
                    Intent intent_notice = new Intent();
                    intent_notice.setClass(getActivity(),NoticeActivity.class);
                    startActivity(intent_notice);
                    break;
                case R.id.iv_interaction:
                    Intent intent_interaction = new Intent();
                    intent_interaction.setClass(getActivity(),InteractionActivity.class);
                    startActivity(intent_interaction);
                    break;
                case R.id.iv_activity:
                    Intent intent_activities = new Intent();
                    intent_activities.setClass(getActivity(),ActivitiesActivity.class);
                    startActivity(intent_activities);
                    break;
            }
        }
    }
}
