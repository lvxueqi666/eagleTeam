package com.example.qiqi.xianwan.zhishidabipin.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.zhishidabipin.activity.SelectActivity;
import com.example.qiqi.xianwan.zhishidabipin.adapter.OptionsListAdapter;
import com.example.qiqi.xianwan.zhishidabipin.bean.QuestionBean;
import com.example.qiqi.xianwan.zhishidabipin.view.NoScrollListView;


@SuppressLint("ValidFragment")
public class QuestionItemFragment extends Fragment {
    private Context context;
    QuestionBean questionBean;
    int index ;
    private OptionsListAdapter adapter;
    private StringBuffer sb;
    private NoScrollListView lv;
    LocalBroadcastManager mLocalBroadcastManager;

    public QuestionItemFragment(int index, Context context){
        this.index = index;
        this.context = context;
        questionBean = SelectActivity.questionlist.get(index);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        View rootView = inflater.inflate(R.layout.pager_item, container, false);
        lv = (NoScrollListView)rootView.findViewById(R.id.lv_options);
        TextView tv_paper_name = (TextView) rootView.findViewById(R.id.tv_paper_name);
        TextView tv_sequence = (TextView) rootView.findViewById(R.id.tv_sequence);
        TextView tv_total_count = (TextView) rootView.findViewById(R.id.tv_total_count);
        TextView tv_description = (TextView) rootView.findViewById(R.id.tv_description);
        adapter = new OptionsListAdapter(getActivity(), questionBean.getQuestionOptions(),lv,index);
        lv.setAdapter(adapter);
        //TODO 展开listvie所有子条目使用了自定义Listview，下面的方法有问题
        //setListViewHeightBasedOnChildren(lv);


        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/fatfish.TTF");
        //使用字体成卡通体
        tv_paper_name.setTypeface(tf);
        tv_total_count.setTypeface(tf);
        tv_sequence.setTypeface(tf);

        tv_paper_name.setText("专项智能练习(言语理解与表达)");
        tv_sequence.setText((index+1)+"");
        tv_total_count.setText("/"+SelectActivity.questionlist.size());

        tv_description.setText(questionBean.getDescription());

        //题干描述前面加上(单选题)或(多选题)
        //判断是单选还是多选
        int questionType = questionBean.getQuestionType();
        sb = new StringBuffer();
        if(questionType == 1){//单选
            SpannableStringBuilder ssb = new SpannableStringBuilder("(单选题)");
            ssb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(questionBean.getDescription());
            tv_description.setTypeface(tf);
            tv_description.setText(ssb);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    adapter.notifyDataSetChanged();
                    //TODO 单选题选中后自动跳转到下一页
                    Intent intent = new Intent("com.leyikao.jumptonext");
                    mLocalBroadcastManager.sendBroadcast(intent);

                }
            });
        }else if(questionType == 2){//多选
            SpannableStringBuilder ssb = new SpannableStringBuilder("(多选题)");
            ssb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(questionBean.getDescription());
            tv_description.setTypeface(tf);
            tv_description.setText(ssb);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    adapter.notifyDataSetChanged();
                }
            });
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void setToast(String text){
        View view = getLayoutInflater().inflate(R.layout.toast_custom, null);
        Toast toast = new Toast(context);
        toast.setView(view);
        TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
        tv_toast.setText(text);
        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/fatfish.TTF");
        //使用字体成卡通体
        tv_toast.setTypeface(tf);
        toast.show();
    }
}
