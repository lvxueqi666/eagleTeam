package com.example.qiqi.xianwan.messageadapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Notice;
import com.example.qiqi.xianwan.messageadapter.noticeadapter.NoticeAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private ImageView iv_back;
    private ListView listView;
    private RefreshLayout notice_srl;
    private List<Notice> noticeList;
    private NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        //注册
        listView = findViewById(R.id.lv_notice);
        iv_back = findViewById(R.id.iv_back);
        notice_srl = findViewById(R.id.notice_srl);

        //返回监听
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //数据初始化
        noticeList = initDatas();

        //adapter
        noticeAdapter = new NoticeAdapter(NoticeActivity.this,R.layout.message_notice_item,noticeList);
        listView.setAdapter(noticeAdapter);


        //SmartRefresh
        //设置刷新事件
        notice_srl.setReboundDuration(1000);
        notice_srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                notice_srl.finishRefresh();
                Toast.makeText(
                        getApplicationContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private Notice ReceiverMsg() {
        Intent noticeIntent = getIntent();
        String action = noticeIntent.getAction();
        if("notice".equals(action)) {
            String noticeMsg = noticeIntent.getStringExtra("content");
            String noticeTitle = noticeIntent.getStringExtra("title");
            String noticeDate = noticeIntent.getStringExtra("date");
            return new Notice(noticeDate, noticeTitle, noticeMsg, R.drawable.product);
        }
        return null;
    }

    private void refreshData() {
        noticeList.clear();
        noticeList.addAll(initDatas());
        noticeAdapter.notifyDataSetChanged();
    }

    private List<Notice> initDatas() {
        List<Notice> noticeList = new ArrayList<>();
        noticeList.add(new Notice("2017年12月17日 08:33","请完成实名认证","按照相关规定，用户需要在规定时间内完善相关信息,查看详细信息>>",R.drawable.notice));
        noticeList.add(new Notice("2018年11月17日 14:33","请完善个人信息","为了您更好的用户体验，请用户完善个人相关信息，以便之后使用，查看详细信息>>",R.drawable.qianbi));
        noticeList.add(new Notice("2019年07月28日 23:33","商品更新","您有新的宝贝已上架，看看这些心仪之物吧！查看详细信息>>",R.drawable.product));
        if(ReceiverMsg() != null) {
            noticeList.add(ReceiverMsg());
        }
        return  noticeList;
    }
}
