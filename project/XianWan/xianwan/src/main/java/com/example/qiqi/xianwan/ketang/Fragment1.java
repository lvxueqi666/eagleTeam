package com.example.qiqi.xianwan.ketang;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qiqi.xianwan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment1 extends Fragment {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<GoodsEntity> goodsEntityList = new ArrayList<GoodsEntity>();
    //自定义recyclerveiw的适配器
    private CollectRecycleAdapter mCollectRecyclerAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json =  (String)msg.obj;
            if(goodsEntityList.size() > 0){
                goodsEntityList.removeAll(goodsEntityList);
            }
            try {
                JSONArray jsonArray = new JSONArray(json);
                for(int i = 0; i < jsonArray.length();i++){
                    String objStr = jsonArray.getString(i);
                    JSONObject jsonObject = new JSONObject(objStr);

                    GoodsEntity goodsEntity = new GoodsEntity(
                            jsonObject.getInt("id"),
                          jsonObject.getInt("type"),
                            jsonObject.getString("imgPath"),
                            jsonObject.getString("introduce"),
                            jsonObject.getString("actor"),
                            jsonObject.getString("videoPath"),
                            jsonObject.getString("content")
                    );
                    goodsEntityList.add(goodsEntity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView);
            //创建adapter
            mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), goodsEntityList);
            //给RecyclerView设置adapter
            mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);

            //item点击事件
            mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, GoodsEntity data) {
                    //此处进行监听事件的业务处理
                    Intent intent=new Intent(getActivity(),DetailActivity.class);
                    intent.putExtra("id",data.getId());
                    intent.putExtra("type",data.getType());
                    intent.putExtra("imgPath",data.getImgPath());
                    intent.putExtra("introduce",data.getIntroduce());
                    intent.putExtra("actor",data.getActor());
                    intent.putExtra("videoPath",data.getVideoPath());
                    intent.putExtra("content",data.getContent());
                    startActivity(intent);
                    Toast.makeText(getActivity(),"我是item", Toast.LENGTH_SHORT).show();
                }
            });




        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       view= inflater.inflate(R.layout.fragment1, container, false);
        //对recycleview进行配置
        initRecyclerView();
        //模拟数据
       // initData();
        asyncFormOp();
        return view;
    }

    private void asyncFormOp() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;

                int type1=1;
                FormBody formBody = new FormBody.Builder()
                        .add("type1", String.valueOf(type1))
                        .build();
                request = new Request.Builder()
                        .url("http://"+hostIp+":8080/XianWanService/ketangForAndroid")
                        .post(formBody)
                        .build();

                //Call
                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message =  response.body().string();
                    wrapperMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }

    private void initData() {
       /* for (int i=0;i<10;i++){
            GoodsEntity goodsEntity=new GoodsEntity();
            goodsEntity.setImgPath("https://tse1-mm.cn.bing.net/th/id/OIP.HoCBss1Q6fGXQbbc-RAVcQHaFj?w=253&h=190&c=7&o=5&pid=1.7");
            goodsEntity.setIntroduce("模拟数据"+i);
            goodsEntity.setActor("100"+i);
            goodsEntityList.add(goodsEntity);
        }*/
       //第一块
        GoodsEntity goodsEntity=new GoodsEntity();

        goodsEntity.setImgPath("https://tse1-mm.cn.bing.net/th/id/OIP.HoCBss1Q6fGXQbbc-RAVcQHaFj?w=253&h=190&c=7&o=5&pid=1.7");
        goodsEntity.setIntroduce("小红帽是德国语言学家雅可布·格林和威廉·格林兄弟收集、整理、加工完成的德国民间文学——《格林童话》中《小红帽》一篇的主人公。\n" +
                "“小红帽”的故事版本多达一百多个，小红帽最早的结局是被邪恶的野狼吞噬。后来，在格林兄弟笔下，勇敢的猎人杀死大野狼，救出了小红帽。在晚近的版本中，又成了小红帽用剪刀剪破大野狼的肚皮，自己拯救了自己。在某些版本中，小红帽甚至成为充满情欲的性感女郎。 [1] \n" +
                "故事讲述了从前有个人见人爱的小姑娘，喜欢戴着外婆送给她的一顶红色天鹅绒的帽子，于是大家就叫她小红帽。有一天，母亲叫她给住在森林的外婆送食物，并嘱咐她不要离开大路，走得太远。小红帽在森林中遇见了狼，她从未见过狼，也不知道狼性凶残，于是告诉了狼她要去森林里看望自己的外婆。狼知道后诱骗小红帽去采野花，自己到林中小屋把小红帽的外婆吃了。后来他伪装成外婆，等小红帽来找外婆时，狼一口把她吃掉了。幸好后来一个勇敢的猎人把小红帽和外婆从狼肚里救了出来。\n" +
                "后来人们就用小红帽来比喻天真幼稚、容易上当受骗的孩子。");
        goodsEntity.setActor("作者:雅可布·格林和威廉·格林兄弟");
        goodsEntityList.add(goodsEntity);
        //第二块
        GoodsEntity goodsEntity2=new GoodsEntity();
        goodsEntity2.setImgPath("https://bkimg.cdn.bcebos.com/pic/b17eca8065380cd7a44ea3e4a144ad3459828162?x-bce-process=image/resize,m_lfit,w_220,h_220,limit_1");
        goodsEntity2.setIntroduce("白雪公主（Snow White）是广泛流行于欧洲的一个童话故事中的人物，其中最著名的故事版本见于德国1812年的《格林童话》。讲述了白雪公主受到继母皇后（格林兄弟最初手稿中为生母）的虐待，逃到森林里，遇到七个小矮人的故事。历史学家巴特尔思据称白雪公主的历史原型是1725年生于德国西部美茵河畔洛尔城的玛利亚·索菲亚·冯·埃尔塔尔。");
        goodsEntity2.setActor("格林兄弟");
        goodsEntityList.add(goodsEntity2);
    }

    private void initRecyclerView() {

        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView);
        //创建adapter
        mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), goodsEntityList);
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, GoodsEntity data) {
                //此处进行监听事件的业务处理
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("id",data.getId());
                intent.putExtra("type",data.getType());
                intent.putExtra("imgPath",data.getImgPath());
                intent.putExtra("introduce",data.getIntroduce());
                intent.putExtra("actor",data.getActor());
                intent.putExtra("videoPath",data.getVideoPath());
                intent.putExtra("content",data.getContent());
                startActivity(intent);
                Toast.makeText(getActivity(),"我是item", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
