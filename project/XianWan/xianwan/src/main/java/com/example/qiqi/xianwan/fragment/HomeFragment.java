package com.example.qiqi.xianwan.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.qiqi.xianwan.R;
import com.example.qiqi.xianwan.entity.Commodity;
import com.example.qiqi.xianwan.homeadapter.EndlessRecyclerOnScrollListener;
import com.example.qiqi.xianwan.homeadapter.GlideImageLoader;
import com.example.qiqi.xianwan.homeadapter.MyRecyclerAdapter;
import com.example.qiqi.xianwan.homeadapter.RecyclerViewSpacesItemDecoration;
import com.example.qiqi.xianwan.homeadapter.SocketConnAsync;
import com.example.qiqi.xianwan.homeadapter.SpaceItem;
import com.example.qiqi.xianwan.homeadapter.SpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View view;
    private TabLayout tabLayout;
    private TabLayoutListener tabLayoutListener;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MyRecyclerAdapter myRecyclerAdapter;
    private List<String> commodityId;
    private List<String> images;
    private List<String> introductions;
    private List<String> price;
    private List<String> icon;
    private List<String> titles;
    private List<String> userId;
    private List<String> userName;
    private List<String> attr;
    private List<String> showLike;
    private List<String> bannerImg;
    private Banner banner;
    private SearchView mSearchView;
    private ListView listView;
    private int mListStyle = 0;
    private int lastVisibleItem = 0;
    private GridLayoutManager mLayoutManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Handler handler;

    public static String hostIp = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view != null){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){
                parent.removeView(view);
            }
        }else {
            view = inflater.inflate(R.layout.home_fragment_layout,container,false);
            //handler,用于在初始化界面时，接收服务器端传来的信息
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
                    initTabLayout();

                }
            };
            initData();
            asyncFormOp();
            findView();
            initSearchView();
            initRefreshLayout();
            initBanner();

        }

        return view;
    }

    //初始化数组
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


    //初始化搜索框
    private void initSearchView(){
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1));
        listView.setTextFilterEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);
                }else{
                    listView.clearTextFilter();
                }
                return false;
            }
        });
    }

    //绑定控件
    private void findView(){
        mSearchView = view.findViewById(R.id.searchView);
        listView = view.findViewById(R.id.listView);
        banner = view.findViewById(R.id.banner);
        tabLayout = view.findViewById(R.id.tabLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
    }

    //自定义刷新样式
    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    //初始化banner
    private void initBanner() {
        bannerImg = new ArrayList<>();
        titles = new ArrayList<>();
        bannerImg.add("http://img4.imgtn.bdimg.com/it/u=4078564255,2150323891&fm=26&gp=0.jpg");
        bannerImg.add("http://img4.imgtn.bdimg.com/it/u=1380751069,2379422871&fm=26&gp=0.jpg");
        bannerImg.add("http://img1.imgtn.bdimg.com/it/u=2276673180,3353400665&fm=26&gp=0.jpg");
        titles.add("1");
        titles.add("2");
        titles.add("3");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerImg);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    //初始化标签卡
    private void initTabLayout(){
        tabLayoutListener = new TabLayoutListener();
        tabLayout.setOnTabSelectedListener(tabLayoutListener);
        tabLayout.addTab(tabLayout.newTab().setText("玩具").setTag(0));
        tabLayout.addTab(tabLayout.newTab().setText("书籍").setTag(1));

    }


    //初始化RecyclerView
    private void initRecyclerView() {
        Resources resources = getActivity().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;//px获取
        final float fontScale = getActivity().getResources().getDisplayMetrics().density;
        int itemWidth = (int) (170 * fontScale + 0.5f);
        int leftDecoration = (width - 2 * itemWidth) / 4;

        HashMap<String,Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,leftDecoration);
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,leftDecoration);

        myRecyclerAdapter = new MyRecyclerAdapter(getActivity(),commodityId,images,introductions,price,icon,userName,userId,attr,showLike,mListStyle,userName.size() > 0 ? true : false);
        recyclerView.setAdapter(myRecyclerAdapter);
        myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                            public void run() {
                                if(mListStyle == 0){
                                    loadMoreData("toy");
                                }else{
                                    loadMoreData("book");
                                }

                            }
                        }, 500);
                        updateRecyclerView();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });

    }


    //更新用于RecyclerView的数据
    private void updateRecyclerView() {
        if (userName.size() > 0) {
            myRecyclerAdapter.updateList(commodityId,images,introductions,price,icon,userName,userId,attr,showLike, true);
        } else {
            myRecyclerAdapter.updateList(null,null,null,null,null, null,null,null,null,false);
        }
    }

    //下拉刷新方法，清空原有数据
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        myRecyclerAdapter.resetDatas();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mListStyle == 0){
                    loadMoreData("toy");
                }else{
                    loadMoreData("book");
                }
            }
        }, 500);
        refreshLayout.setRefreshing(false);
        updateRecyclerView();
    }


    //TabLayout选中事件
    class TabLayoutListener implements TabLayout.OnTabSelectedListener{

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            Log.e("anle","anle");
            mListStyle = (int)tab.getTag();

            recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }


    private void asyncFormOp() {
        Resources resources = getResources();
        hostIp = resources.getString(R.string.hostStr);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Log.e("jinlaile", "haha");
                Request request;
                //Request(Post、FormBody）

                FormBody formBody = new FormBody.Builder()
                        .add("type", "toy")
                        .build();
                request = new Request.Builder()
                        .url("http://"+hostIp+":8080/XianWanService/HomeForAndroid")
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


    //加载更多数据
    private void loadMoreData(final String type){
        Resources resources = getResources();
        final String hostIp = resources.getString(R.string.hostStr);
        initData();
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Log.e("jinlaile", "haha");
                Request request;
                if(type.equals("toy")){
                    FormBody formBody = new FormBody.Builder()
                            .add("type", "toy")
                            .build();
                    request = new Request.Builder()
                            .url("http://"+hostIp+":8080/XianWanService/HomeForAndroid")
                            .post(formBody)
                            .build();
                }else{
                    FormBody formBody = new FormBody.Builder()
                            .add("type", "book")
                            .build();
                    request = new Request.Builder()
                            .url("http://"+hostIp+":8080/XianWanService/HomeForAndroid")
                            .post(formBody)
                            .build();
                }

                Call call = okHttpClient.newCall(request);

                Response response;

                try {
                    response = call.execute();
                    String message = response.body().string();
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
