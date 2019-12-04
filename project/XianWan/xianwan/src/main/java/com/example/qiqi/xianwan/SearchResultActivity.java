package com.example.qiqi.xianwan;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.qiqi.xianwan.homeadapter.RecyclerViewSpacesItemDecoration;
import com.example.qiqi.xianwan.homeadapter.SearchRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchResultActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private String content;
    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;
    private SearchRecyclerAdapter searchRecyclerAdapter;
    private List<String> commodityId;
    private List<String> images;
    private List<String> introductions;
    private List<String> price;
    private List<String> icon;
    private List<String> userId;
    private List<String> userName;
    private List<String> attr;
    private List<String> showLike;
    private SwipeRefreshLayout refreshLayout;
    private Handler handler;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent  = getIntent();
        content = intent.getStringExtra("content");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                Log.e("jsonjsonjson",json);
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for(int i = 0; i < jsonArray.length();i++){
                        String objStr = jsonArray.getString(i);
                        JSONObject jsonObject = new JSONObject(objStr);
                        commodityId.add(jsonObject.getString("id"));
                        images.add(jsonObject.getString("image"));
                        introductions.add(jsonObject.getString("introduce"));
                        price.add(jsonObject.getString("price"));
                        icon.add(jsonObject.getString("icon"));
                        userName.add(jsonObject.getString("userName"));
                        userId.add(jsonObject.getString("userId"));
                        attr.add(jsonObject.getString("attr"));
                        showLike.add(jsonObject.getString("showLike"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initRecyclerView();

            }
        };
        initData();
        findView();
        loadSearchData();
        initRefreshLayout();
    }

    private void initData(){
        commodityId = new ArrayList<>();
        images = new ArrayList<>();
        introductions = new ArrayList<>();
        price = new ArrayList<>();
        icon = new ArrayList<>();
        userName = new ArrayList<>();
        userId = new ArrayList<>();
        attr = new ArrayList<>();
        showLike = new ArrayList<>();
    }

    private void findView(){
        recyclerView = findViewById(R.id.recyclerViewForSearch);
        refreshLayout = findViewById(R.id.refreshLayoutForSearch);
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;//px获取
        final float fontScale = getApplicationContext().getResources().getDisplayMetrics().density;
        int itemWidth = (int) (170 * fontScale + 0.5f);
        int leftDecoration = (width - 2 * itemWidth) / 4;

        HashMap<String,Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,leftDecoration);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,leftDecoration);

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        searchRecyclerAdapter = new SearchRecyclerAdapter(getApplicationContext(),commodityId,images,introductions,price,icon,userName,userId,attr,showLike,userName.size() > 0 ? true : false);
        recyclerView.setAdapter(searchRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        if(recyclerView.getItemDecorationCount() == 0)
            recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(!recyclerView.canScrollVertically(1)){
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() { loadMoreData();
                            }
                        }, 500);
                        updateRecyclerView();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void updateRecyclerView() {
        if (userName.size() > 0) {
            searchRecyclerAdapter.updateList(commodityId,images,introductions,price,icon,userName,userId,attr,showLike, true);
        } else {
            searchRecyclerAdapter.updateList(null,null,null,null,null, null,null,null,null,false);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        searchRecyclerAdapter.resetDatas();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreData();
            }
        }, 500);
        refreshLayout.setRefreshing(false);
        updateRecyclerView();
    }

    private void loadSearchData() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;

                FormBody formBody = new FormBody.Builder()
                        .add("content", content)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/SearchOperate")
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

    private void loadMoreData() {
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        initData();
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request;

                FormBody formBody = new FormBody.Builder()
                        .add("content", content)
                        .build();
                request = new Request.Builder()
                        .url("http://" + hostIp + ":8080/XianWanService/SearchOperate")
                        .post(formBody)
                        .build();

                //Call
                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message =  response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(message);
                        for(int i = 0; i < jsonArray.length();i++){
                            String objStr = jsonArray.getString(i);
                            JSONObject jsonObject = new JSONObject(objStr);
                            commodityId.add(jsonObject.getString("id"));
                            images.add(jsonObject.getString("image"));
                            introductions.add(jsonObject.getString("introduce"));
                            price.add(jsonObject.getString("price"));
                            icon.add(jsonObject.getString("icon"));
                            userName.add(jsonObject.getString("userName"));
                            userId.add(jsonObject.getString("userId"));
                            attr.add(jsonObject.getString("attr"));
                            showLike.add(jsonObject.getString("showLike"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }

}
