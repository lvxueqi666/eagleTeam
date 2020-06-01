package com.example.qiqi.xianwan.zhishidabipin.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.zhishidabipin.adapter.ItemAdapter;
import com.example.qiqi.xianwan.zhishidabipin.bean.QuestionBean;
import com.example.qiqi.xianwan.zhishidabipin.bean.QuestionOptionBean;
import com.example.qiqi.xianwan.zhishidabipin.transformer.ScalePageTransformer;
import com.example.qiqi.xianwan.zhishidabipin.view.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends FragmentActivity implements View.OnClickListener {
    List<View> list = new ArrayList<View>();
    public static List<QuestionBean> questionlist = new ArrayList<QuestionBean>();
    public static QuestionBean question;
    public List<QuestionOptionBean> options1 = new ArrayList<QuestionOptionBean>();
    public List<QuestionOptionBean> options2 = new ArrayList<QuestionOptionBean>();
    public List<QuestionOptionBean> options3 = new ArrayList<QuestionOptionBean>();
    public List<QuestionOptionBean> options4 = new ArrayList<QuestionOptionBean>();
    public static QuestionOptionBean option;
    private ViewPager vp;
    private ItemAdapter pagerAdapter;
    View pager_item;
    public static int currentIndex = 0;
    private TextView tv_time;
    private TextView tv_share;
    private TextView tv_answercard;
    private TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        loadData();


        Log.e("测试数据", questionlist.get(0).toString());
        Log.e("测试数据", questionlist.get(1).toString());

        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_answercard = (TextView) findViewById(R.id.tv_answercard);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_share = (TextView) findViewById(R.id.tv_share);
        startCounter();
        tv_back.setOnClickListener(this);
        tv_answercard.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_share.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.vp);

        vp.setPageTransformer(true,new ScalePageTransformer());
        vp.setCurrentItem(0);
        pagerAdapter = new ItemAdapter(getSupportFragmentManager(),SelectActivity.this);
        vp.setAdapter(pagerAdapter);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int position) {
                currentIndex = position;
            }
        });

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.leyikao.jumptonext");
        filter.addAction("com.leyikao.jumptopage");
        lbm.registerReceiver(mMessageReceiver, filter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back://点击头部返回
                finish();
                break;
            case R.id.tv_answercard://点击头部答题卡

                jumpToPage(questionlist.size());

                break;
            case R.id.tv_time://点击头部计时器
                //TODO计时器停止计时
                stopCounter();
                final ConfirmDialog confirmDialog = new ConfirmDialog(this, "共4道题，还剩4道题未做");
                confirmDialog.setCancelable(false);
                confirmDialog.show();
                confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {

                    @Override
                    public void doProceed() {
                        //TODO计时器继续计时
                        confirmDialog.dismiss();
                        startCounter();
                    }

                });
                break;
            case R.id.tv_share://点击头部分享

                break;
            default:
                break;
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.leyikao.jumptonext")) {
                jumpToNext() ;
            } else if (intent.getAction().equals("com.leyikao.jumptopage")) {
                int index = intent.getIntExtra("index", 0);
                jumpToPage(index);
            }
        }
    };

    public void jumpToNext() {
        int position = vp.getCurrentItem();
        vp.setCurrentItem(position + 1);

    }
    public void jumpToPage(int index) {
        vp.setCurrentItem(index);
    }

    private void loadData() {
        // 初始化数据
        option = new QuestionOptionBean("A", "要我们多多吃饭，长高个！");
        options1.add(option);
        option = new QuestionOptionBean("B", "要我们珍惜粮食，要知道农民的辛苦。");
        options1.add(option);
        option = new QuestionOptionBean("C", "告诉我们中午要种田！");
        options1.add(option);
        option = new QuestionOptionBean("D", "要我们知道粮食是农民伯伯种出来的。");
        options1.add(option);
        question = new QuestionBean("0001", "《悯农》"
                + "\n李绅" + "\n锄禾日当午，" + "\n汗滴禾下土。" + "\n谁知盘中餐，" + "\n粒粒皆辛苦。"
                + "\n这首诗告诫我们什么呢？", 1, "常识判断", "001", options1);
        questionlist.add(question);

        // 初始化数据
        option = new QuestionOptionBean("A", "河北");
        options2.add(option);
        option = new QuestionOptionBean("B", "通州");
        options2.add(option);
        option = new QuestionOptionBean("C", "石家庄");
        options2.add(option);
        option = new QuestionOptionBean("D", "北京");
        options2.add(option);
        question = new QuestionBean("0002", "中国的首都在哪？", 1, "常识判断", "001",
                options2);
        questionlist.add(question);

        // 初始化数据
        option = new QuestionOptionBean("A",
                "老虎");
        options3.add(option);
        option = new QuestionOptionBean("B",
                "狮子");
        options3.add(option);
        option = new QuestionOptionBean("C",
                "熊猫");
        options3.add(option);
        option = new QuestionOptionBean("D",
                "袋鼠");
        options3.add(option);
        question = new QuestionBean("0003", "哪个是我国的国宝呢？", 1, "常识判断",
                "001", options3);
        questionlist.add(question);

        // 初始化数据
        option = new QuestionOptionBean("A",
                "五颗星星");
        options4.add(option);
        option = new QuestionOptionBean("B",
                "六颗星星");
        options4.add(option);
        option = new QuestionOptionBean("C",
                "七颗星星");
        options4.add(option);
        option = new QuestionOptionBean("D",
                "八颗星星");
        options4.add(option);
        question = new QuestionBean("0004", "“五星红旗迎风飘扬，胜利歌声多么响亮~”，我国国旗上有几颗星星呢？ ", 1, "常识判断",
                "001", options4);
        questionlist.add(question);

    }
    //计时器任务
    int time = 0;
    int second = 0;
    int minute = 0;
    String timeStr  ="00:00";
    int[] iTime = new int[]{0,0,0,0};
    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    time++;
                    second = time %60;
                    minute = time /60;
                    if(minute>99){
                        break;
                    }
                    //Log.e("秒数", ""+second);
                    //Log.e("分钟数", ""+minute);
                    if(second < 10 && minute < 10){
                        iTime[0]=0;
                        iTime[1]=minute;
                        iTime[2]=0;
                        iTime[3]=second;

                    }else if(second >= 10 && minute < 10){
                        iTime[0]=0;
                        iTime[1]=minute;
                        iTime[2]=(second+"").charAt(0)-48;
                        iTime[3]=(second+"").charAt(1)-48;

                    }else if(second < 10 && minute >= 10){
                        iTime[0]=(minute+"").charAt(0)-48;
                        iTime[1]=(minute+"").charAt(1)-48;
                        iTime[2]=0;
                        iTime[3]=second;

                    }else if(second >= 10 && minute >= 10){
                        iTime[0]=(minute+"").charAt(0)-48;
                        iTime[1]=(minute+"").charAt(1)-48;
                        iTime[2]=(second+"").charAt(0)-48;
                        iTime[3]=(second+"").charAt(1)-48;

                    }
                    tv_time.setText(""+iTime[0]+iTime[1]+":"+iTime[2]+iTime[3]);
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;

                default:
                    break;
            }

        };
    };


    // 开始计时
    public void startCounter() {
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    // 暂停计时
    public void stopCounter() {
        handler.removeCallbacksAndMessages(null);
    }

    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
        super.onDestroy();
    }


}
