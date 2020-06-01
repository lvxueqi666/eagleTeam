package com.example.qiqi.xianwan.ketang;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

public class Fragment2 extends Fragment {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<GoodsEntity> goodsEntityList = new ArrayList<GoodsEntity>();
    //自定义recyclerveiw的适配器
    private CollectRecycleAdapter2 mCollectRecyclerAdapter;
    private GoodsEntity goodsEntity;
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


            mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView2);
            //创建adapter
            mCollectRecyclerAdapter = new CollectRecycleAdapter2(getActivity(), goodsEntityList);
            //给RecyclerView设置adapter
            mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);

            //item点击事件
            mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter2.OnItemClickListener() {
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

                }
            });




        }

    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment2, container, false);
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

                int type1=2;
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
        goodsEntity.setImgPath("https://bkimg.cdn.bcebos.com/pic/4034970a304e251f46b43366a586c9177e3e53e9?x-bce-process=image/resize,m_lfit,w_250,h_250,limit_1");
        goodsEntity.setIntroduce("《春江花月夜》是唐代诗人张若虚的诗作。此诗沿用陈隋乐府旧题，运用富有生活气息的清丽之笔，以月为主体，以江为场景，描绘了一幅幽美邈远、惝恍迷离的春江月夜图，抒写了游子思妇真挚动人的离情别绪以及富有哲理意味的人生感慨，表现了一种迥绝的宇宙意识，创造了一个深沉、寥廓、宁静的境界。全诗共三十六句，每四句一换韵，通篇融诗情、画意、哲理为一体，意境空明，想象奇特，语言自然隽永，韵律宛转悠扬，洗净了六朝宫体的浓脂腻粉，具有极高的审美价值，素有“孤篇盖全唐”之誉。");
        goodsEntity.setActor("张若虚");
        goodsEntityList.add(goodsEntity);
        //第二块
        GoodsEntity goodsEntity2=new GoodsEntity();
        goodsEntity2.setImgPath("http://pics.shicimingju.com/item/26317.jpg");
        goodsEntity2.setIntroduce("《蜀道难》是中国唐代大诗人李白的代表作品。此诗袭用乐府旧题，以浪漫主义的手法，展开丰富的想象，艺术地再现了蜀道峥嵘、突兀、强悍、崎岖等奇丽惊险和不可凌越的磅礴气势，借以歌咏蜀地山川的壮秀，显示出祖国山河的雄伟壮丽，充分显示了诗人的浪漫气质和热爱自然的感情。全诗二百九十四字，采用律体与散文间杂，文句参差，笔意纵横，豪放洒脱，感情强烈，一唱三叹。诗中诸多的画面此隐彼现，无论是山之高，水之急，河山之改观，林木之荒寂，连峰绝壁之险，皆有逼人之势，气象宏伟，境界阔大，集中体现了李白诗歌的艺术特色和创作个性，深受学者好评，被誉为“奇之又奇”之作。");
        goodsEntity2.setActor("李白");
        goodsEntityList.add(goodsEntity2);
        //第三块
        GoodsEntity goodsEntity3=new GoodsEntity();
        goodsEntity3.setImgPath("https://bkimg.cdn.bcebos.com/pic/35a85edf8db1cb138ea4c5e6d154564e93584b72?x-bce-process=image/resize,m_lfit,w_220,h_220,limit_1");
        goodsEntity3.setIntroduce("《离骚》是中国战国时期诗人屈原创作的诗篇，是中国古代最长的抒情诗。此诗以诗人自述身世、遭遇、心志为中心。前半篇反复倾诉诗人对楚国命运和人民生活的关心，表达要求革新政治的愿望，和坚持理想、虽逢灾厄也绝不与邪恶势力妥协的意志；后半篇通过神游天界、追求实现理想和失败后欲以身殉的陈述，反映出诗人热爱国家和人民的思想感情。全诗运用美人香草的比喻、大量的神话传说和丰富的想象，形成绚烂的文采和宏伟的结构，表现出积极的浪漫主义精神，并开创了中国文学史上的“骚体”诗歌形式，对后世产生了深远的影响。其主要注本有东汉王逸的《楚辞章句》、南宋朱熹的《楚辞集注》、清代戴震的《屈原赋注》等。");
        goodsEntity3.setActor("屈原");
        goodsEntityList.add(goodsEntity3);
    }

    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView2);
        //创建adapter
        mCollectRecyclerAdapter = new CollectRecycleAdapter2(getActivity(), goodsEntityList);
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //两列显示
        StaggeredGridLayoutManager staggered2=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mCollectRecyclerView.setLayoutManager(staggered2);
        //设置item的分割线
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        Drawable drawable = getResources().getDrawable(R.drawable.divider_recyclerview);
        decoration.setDrawable(drawable);
        mCollectRecyclerView.addItemDecoration(decoration);

        // mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter2.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, GoodsEntity data) {
                //此处进行监听事件的业务处理
                Intent intent=new Intent();
                intent.putExtra("id",data.getId());
                intent.putExtra("type",data.getType());
                intent.putExtra("imgPath",data.getImgPath());
                intent.putExtra("introduce",data.getIntroduce());
                intent.putExtra("actor",data.getActor());
                intent.putExtra("videoPath",data.getVideoPath());
                intent.putExtra("content",data.getContent());

                 intent.setClass(getActivity(),DetailActivity.class);
                 startActivity(intent);
                Toast.makeText(getActivity(),"我是item", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
