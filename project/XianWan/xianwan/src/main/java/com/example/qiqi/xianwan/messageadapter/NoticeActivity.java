package com.example.qiqi.xianwan.messageadapter;

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

    private void refreshData() {
        noticeList.clear();
        noticeList.addAll(initDatas());
        noticeAdapter.notifyDataSetChanged();
    }

    private List<Notice> initDatas() {
        List<Notice> noticeList = new ArrayList<>();
        noticeList.add(new Notice("2017年12月17日 08:33","请完成实名认证","5555555555555555511111111111111111115555>>",R.drawable.touxiang));
        noticeList.add(new Notice("2018年11月17日 14:33","请完成实名认证","66666666666666666666666666666666666666666666>>",R.drawable.touxiang));
        noticeList.add(new Notice("2019年07月28日 23:33","请完成实名认证","55588888888888888888777777777777777777755>>",R.drawable.touxiang));
        return  noticeList;
    }
}
