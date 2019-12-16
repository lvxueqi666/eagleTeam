package com.example.qiqi.xianwan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiqi.xianwan.ConversationActivity;
import com.example.qiqi.xianwan.LoginActivity;
import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Message;
import com.example.qiqi.xianwan.messageadapter.ActivitiesActivity;
import com.example.qiqi.xianwan.messageadapter.MessageAdapter;
import com.example.qiqi.xianwan.messageadapter.MessageChatActivity;
import com.example.qiqi.xianwan.messageadapter.NoticeActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

import static com.example.qiqi.xianwan.LoginActivity.USERACCOUNT;
import static com.example.qiqi.xianwan.LoginActivity.USERNAME;


public class MessageFragment extends Fragment {
    private ImageView iv_notice;
    private ImageView iv_interaction;
    private ImageView iv_activities;
    private ListView lv_message;
    private List<Message>  messageList;
    private SmartRefreshLayout srl;
    private MessageAdapter adapter;
    private CustomOnClickListener listener;
    private EMMessageListener emMessageListener;
    private  int unreadMsgNum;
    private TextView tv_unread2;

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
        tv_unread2 = view.findViewById(R.id.tv_unread2);

        //注册点击监听
        registerListenrs();

        //数据初始化
        if(USERNAME != null && USERACCOUNT != null) {
            messageList = initData();
        }else{
            messageList = Data();
        }

        //列表点击事件
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(USERNAME != null && USERACCOUNT != null) {
                    Message message = messageList.get(position);
                    Toast.makeText(
                            getActivity(),
                            message.getUserName(),
                            Toast.LENGTH_SHORT
                    ).show();
                    Intent intent = new Intent(getActivity(), MessageChatActivity.class);
                    intent.putExtra("name", message.getUserName());
                    startActivity(intent);
                }else{
                    Intent intent_notice = new Intent();
                    intent_notice.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent_notice);
                }
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

        emMessageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                Log.i("wtw", "成功接受到消息");
                unreadMsgNum = EMClient.getInstance().chatManager().getUnreadMessageCount();
                new Thread() {
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_unread2.setText("" + unreadMsgNum);
                            }

                        });
                    }

                }.start();
                tv_unread2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                Log.i("wtw","成功接受到消息");
                unreadMsgNum = EMClient.getInstance().chatManager().getUnreadMessageCount();
                tv_unread2.setText("" + unreadMsgNum);
                tv_unread2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        Log.i("wtw","注册消息监听器");
        unreadMsgNum = EMClient.getInstance().chatManager().getUnreadMessageCount();
        if(unreadMsgNum > 0){
            tv_unread2.setText("" + unreadMsgNum);
        }else{
            tv_unread2.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
        Log.i("wtw","取消注册消息监听器");
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
        messageList.add(new Message("闲玩","信誉良好","七天前",R.drawable.aa,R.drawable.tubiao));
        return messageList;
    }

    //默认数据插入
    private List<Message> Data() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("闲玩","信誉良好","七天前",R.drawable.aa,R.drawable.tubiao));
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
                    if(USERNAME != null && USERACCOUNT != null){
                        Intent intent_notice = new Intent();
                        intent_notice.setClass(getActivity(), NoticeActivity.class);
                        startActivity(intent_notice);
                    }else{
                        Intent intent_notice = new Intent();
                        intent_notice.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent_notice);
                    }
                    break;
                case R.id.iv_interaction:
                    if(USERNAME != null && USERACCOUNT != null) {
                        Intent intent_interaction = new Intent();
                        intent_interaction.setClass(getActivity(), ConversationActivity.class);
                        startActivity(intent_interaction);
                    }else{
                        Intent intent_notice = new Intent();
                        intent_notice.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent_notice);
                    }
                    break;
                case R.id.iv_activity:
                    if(USERNAME != null && USERACCOUNT != null) {
                        Intent intent_activities = new Intent();
                        intent_activities.setClass(getActivity(), ActivitiesActivity.class);
                        startActivity(intent_activities);
                    }else{
                        Intent intent_notice = new Intent();
                        intent_notice.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent_notice);
                    }
                    break;
            }
        }
    }
}
