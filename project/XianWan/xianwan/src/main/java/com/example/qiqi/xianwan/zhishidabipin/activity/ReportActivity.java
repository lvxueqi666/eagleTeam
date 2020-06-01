package com.example.qiqi.xianwan.zhishidabipin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;

public class ReportActivity extends AppCompatActivity {
    LocalBroadcastManager mLocalBroadcastManager;
    int count = SelectActivity.questionlist.size();
    int[] mIds = new int[count];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initData();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        GridView gv = (GridView) findViewById(R.id.report_gv);
        TextView tv_report_title = (TextView)findViewById(R.id.tv_report_title);
        TextView tv_report_total_question = (TextView) findViewById(R.id.tv_report_total_question);
        TextView tv_report_exam_type = (TextView) findViewById(R.id.tv_report_exam_type);
        TextView tv_submit_time = (TextView)findViewById(R.id.tv_submit_time);
        TextView tv_true = (TextView)findViewById(R.id.tv_true);
        TextView tv_numone = (TextView)findViewById(R.id.tv_numone);
        TextView tv_wrong_analysis = (TextView)findViewById(R.id.tv_wrong_analysis);
        TextView tv_all_analysis = (TextView)findViewById(R.id.tv_all_analysis);
        TextView tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/fatfish.TTF");
        //使用字体成卡通体
        tv_report_title.setTypeface(tf);
        tv_report_total_question.setTypeface(tf);
        tv_report_exam_type.setTypeface(tf);
        tv_submit_time.setTypeface(tf);
        tv_true.setTypeface(tf);
        tv_numone.setTypeface(tf);
        tv_wrong_analysis.setTypeface(tf);
        tv_all_analysis.setTypeface(tf);

        RelativeLayout rl_result_panel = (RelativeLayout) findViewById(R.id.rl_result_panel);
        //设置scrollview 自动置顶
        rl_result_panel.setFocusable(true);
        rl_result_panel.setFocusableInTouchMode(true);
        rl_result_panel.requestFocus();

        tv_report_total_question.setText("道/"+count+"道");
        MyAdapter adapter = new MyAdapter(this);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO跳转到相应的viewpager 页面
                Intent intent = new Intent("com.leyikao.jumptopage");
                intent.putExtra("index", position);
                mLocalBroadcastManager.sendBroadcast(intent);
            }
        });
    }
    private void initData() {
        for (int i = 0; i < count; i++) {
            mIds[i] = i + 1;
        }
    }

    private class MyAdapter extends BaseAdapter {

        private Context mContext;

        public MyAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(mContext);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new GridView.LayoutParams(80, 80));
            tv.setPadding(8, 8, 8, 8);

            //将字体文件保存在assets/fonts/目录下，创建Typeface对象
            Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"fonts/fatfish.TTF");
            //使用字体成卡通体
            tv.setTypeface(tf);

            tv.setText(mIds[position] + "");
            tv.setBackgroundResource(R.drawable.option_btn_single_normal);
            return tv;
        }

    }

}

