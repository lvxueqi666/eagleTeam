package com.example.qiqi.xianwan.ketang;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
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

public class Search_Result extends AppCompatActivity {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView searchrecyclerview;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<GoodsEntity> goodsEntityList = new ArrayList<GoodsEntity>();
    public String introduce;
    private Button search_back;
    //自定义recyclerveiw的适配器
    private CollectRecycleAdapter2 mCollectRecyclerAdapter;
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


            searchrecyclerview=findViewById(R.id.search_recyclerview);
            //创建adapter
            mCollectRecyclerAdapter = new CollectRecycleAdapter2(getApplicationContext(), goodsEntityList);
            //给RecyclerView设置adapter
            searchrecyclerview.setAdapter(mCollectRecyclerAdapter);
            //item点击事件
            mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter2.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, GoodsEntity data) {
                    //此处进行监听事件的业务处理
                    Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ketang);
        //接收信息
        searchrecyclerview=findViewById(R.id.search_recyclerview);

        Intent intent  = getIntent();
        introduce = intent.getStringExtra("introduce");



        //对recycleview进行配置
        initRecyclerView();
        //模拟数据
        // initData();
        asyncFormOp();
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
                        .add("introduce",introduce)
                        .build();
                request = new Request.Builder()
                        .url("http://"+hostIp+":8080/XianWanService/SearchForAndroid")
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

    }

    private void initRecyclerView() {
        //创建adapter
        //给RecyclerView设置adapte
        //获取RecyclerView

        //创建adapter
        mCollectRecyclerAdapter = new CollectRecycleAdapter2(getApplicationContext(), goodsEntityList);
        //给RecyclerView设置adapter
        searchrecyclerview.setAdapter(mCollectRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        searchrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        StaggeredGridLayoutManager staggered2=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        searchrecyclerview.setLayoutManager(staggered2);
        //设置item的分割线
        DividerItemDecoration decoration = new DividerItemDecoration(getApplication(), DividerItemDecoration.VERTICAL);
        Drawable drawable = getResources().getDrawable(R.drawable.divider_recyclerview);
        decoration.setDrawable(drawable);
        searchrecyclerview.addItemDecoration(decoration);
        //设置item的分割线

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

                intent.setClass(getApplicationContext(),DetailActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"我是item", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
