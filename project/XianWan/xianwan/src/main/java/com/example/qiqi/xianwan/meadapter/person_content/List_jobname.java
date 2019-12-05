package com.example.qiqi.xianwan.meadapter.person_content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.qiqi.xianwan.meadapter.MessageEvent;
import com.example.qiqi.xianwan.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;


public class List_jobname extends Activity{
    private String[] names;//模拟数据源
    private ArrayList<HashMap<String,String>> listItem;//需求的数据结构
    private ListView mListView;//列表对象
    private Button jobname_back;
    MessageEvent mObjEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_jobname_lv);
        EventBus.getDefault().register(this);
        jobname_back=findViewById(R.id.jobname_back);
        initCtrl();//初始化组件
        jobname_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mObjEvent) {


                    Intent intent = new Intent(List_jobname.this, person_content.class);
                    intent.putExtra("jobname", mObjEvent.getMessage());
                    setResult(3, intent);
                    finish();
                }
            }
        });
        mListView.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Intent intent=new Intent(List_jobname.this,person_content.class);

                //给message起一个名字，并传给另一个activity
                intent.putExtra("jobname",names[arg2]);
                setResult(3, intent);
                //启动意图
                finish();

            }
        });
    }

    /*初始化组件*/
    private void initCtrl() {
        mListView=findViewById(R.id.lv_jobname);//获得listView对象
        listItem=loadData();//加载数据
        SimpleAdapter listItemAdapter=new SimpleAdapter(getBaseContext(),/*指明了SimpleAdapter关联的View的运行环境，也就是当前的Activity*/
                listItem,/*由Map组成的List,在List中的每条目对应ListView的一行，每一个Map中包含的就是所有在from参数中指定的key*/
                R.layout.me_jobname_item,/*定义列表项的布局文件的资源ID，该资源文件至少应该包含在to参数中定义的ID*/
                new String[]{"ItemName"},/*将被添加到Map映射上的Key*/
                new int[] {R.id.jobname}/*将绑定数据的视图的Id跟from参数对应，这些被绑定的视图元素应该全是TextView*/
        );
        //设置适配器
        mListView.setAdapter(listItemAdapter);
    }
    //重写物理返回按钮
    @Override
    public void onBackPressed() {
        Toast.makeText(List_jobname.this, "请选择一个职业名称哦!", Toast.LENGTH_LONG).show();
    }
    /*模拟获取数据源过程*/
    private ArrayList<HashMap<String, String>> loadData() {
        names=new String[]{"创始人","高管","职业经理人","市场","销售","人力资源","行政","公关","客服","财务","法务","工程师","设计师","分析师","产品经理","咨询顾问","研究员","教师","演员","教练"};
        listItem=new ArrayList<HashMap<String,String>>();
        //遍历数组
        for(int i=0;i<names.length;i++){
            HashMap<String,String> map=new HashMap<String,String>();
            String name=names[i];
            map.put("ItemName", name);//以键值对的形式保存
            listItem.add(map);//将HashMap添加到list中
        }
        return listItem;
    }
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onMoonEvent(MessageEvent objEvent) {
        if (null != objEvent) {
            //赋值
            this.mObjEvent = objEvent;
        }
    }


    @Override
    protected void onDestroy() {
        //移除全部粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //解绑事件
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
